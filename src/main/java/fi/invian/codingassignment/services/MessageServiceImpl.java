package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.MessagePojo;

public class MessageServiceImpl implements MessageService {

	@Override
	public Response sendNewMessage(MessagePojo message) throws SQLException {

		/*
		 * TODO HEIKKI(Optimization, Logging) Test will log4j asynchronous logging work better or not. Other joice is to use java.util.logging
		 */
		System.out.println("Trying to send following message " + message);

		Connection connection = null;

		try {

			connection = DatabaseConnection.getConnection();
			
			if (!userExistsInDb(message.getSenderId(), connection)) { //Nesting connection not recommended, sending connection.
				return Response.status(Response.Status.NOT_FOUND).entity("Not Found: Sender not found from the system.").build();
			}

			connection.setAutoCommit(false); // auto-commit false, ready for transaction.


			String sqlForMessagesTable = "INSERT INTO Messages (title, body, sender_id) VALUES (?, ?, ?)"; // For Messages Table
			PreparedStatement preparedStatementForMessages = connection.prepareStatement(sqlForMessagesTable,
					Statement.RETURN_GENERATED_KEYS); // We want to use RETURN_GENERATED_KEYS. Inserted id will return
														// from
														// db.

			preparedStatementForMessages.setString(1, message.getTitle());
			preparedStatementForMessages.setString(2, message.getBody());
			preparedStatementForMessages.setInt(3, message.getSenderId());

			int rowsAffectedFromMessagesTable = preparedStatementForMessages.executeUpdate();
			if (rowsAffectedFromMessagesTable > 0) { // Insert was successful

				ResultSet generatedKeys = preparedStatementForMessages.getGeneratedKeys();

				if (generatedKeys.next()) {
					int message_id = generatedKeys.getInt(1); // For follow up query. Only first one is
																// important. TODO MINOR: If rollbacks has been
																// happened, db id is still counting it for next id.
					
					for (Integer receaver_id : message.getReceiverIds()) {

						/*
						 * TODO HEIKKI(Optimization, Query) Check the existence of multiple users in a single query and return the list of valid ones. Should insert to Recipients be done as one big, not individual inserts?
						 * TODO HEIKKI(Bug, Query) When there is no  Receivers, system sends Message  even if there were no receivers
						 */
						if (!userExistsInDb(receaver_id, connection)) {
							System.out.println("Receiver with user_id " + receaver_id + " does not exist. Skipping this user_id for sending message");
							continue;
						}
						
						String sqlForRecipientsTable = "INSERT INTO Recipients (message_id, receiver_id) VALUES (?, ?)"; // For
																															// Recipients
						System.out.println("Sending message with message_id is: " + message_id);
						System.out.println("Sending message with receaver_id is: " + receaver_id);

						PreparedStatement preparedStatement = connection.prepareStatement(sqlForRecipientsTable);
						preparedStatement.setInt(1, message_id);
						preparedStatement.setInt(2, receaver_id);
						int executeUpdate = preparedStatement.executeUpdate();

						System.out.println("Insert was " + (executeUpdate > 0 ? "successful" : "not successful"));
					}
				} else {

					// GeneratedKeys not supported by database.
					// Other logic to get inserted Message row id from db.
					// For now just rollback
					connection.rollback();
					throw new SQLException("No generated keys received. Abort Actions");

				}

				connection.commit();
				
				System.out.println("Message created");
				
				// Following The choice of the message content in the entity depends on the design and requirements of your API. 
				return Response.status(Response.Status.CREATED).type(MediaType.APPLICATION_JSON).entity("Created: The message was sent successfully.")
						.build();
			} else {

				connection.rollback();
				return Response.status(Response.Status.CONFLICT)
						.entity("Conflict: The record conflicting with record on database").build();
			}
		} catch (SQLException e) {
			// Something unexpected happened inside db logic
			connection.rollback();
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error: Unique constraint violation or SQL error").build();

		} finally { // For closing connection and resetting auto-commit
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // Reset auto-commit
					connection.close();
				} catch (SQLException e) {
					// Error while closing connection
					connection.rollback();
					e.printStackTrace();
				}
			}

		}
	}

	/*	Todo HEIKKI(Optimization, Query) Look if these matter after get timing calculator working  
	  	Method 1: Using EXISTS Subquery ✔️
		Method 2: Using LIMIT with SELECT 
		Method 3: Using COUNT with SELECT ❌
			- COUNT(*) to check existence is less efficient than using EXISTS or IN because it may need to count all matching rows.
		Method 4: Using FETCH with SELECT
	 */
	private boolean userExistsInDb(int user_id, Connection connection) throws SQLException {// Nesting connection not
		
		String sqlForUsersTable = "SELECT EXISTS (SELECT 1 FROM Users WHERE user_id = ?) AS user_exists";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForUsersTable)) {

			preparedStatement.setInt(1, user_id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					
					return resultSet.getBoolean("user_exists");
				}
			}
		}
		
		return false;
	}

	@Override
	public Response getMessagesForAdressedUser(int userId) throws SQLException {
		
		System.out.println("Trying to get messages for " + userId);
		
		String sqlForGettingMessagesForUser = "SELECT * FROM Messages JOIN Recipients ON Messages.message_id = Recipients.message_id  WHERE Recipients.receiver_id = ?";

		try (Connection connection = DatabaseConnection.getConnection()) {

			if (!userExistsInDb(userId, connection)) { //Nesting connection not recommended, sending connection.
				return Response.status(Response.Status.NOT_FOUND).entity("Not Found: User not found from the system.").build();
			}
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlForGettingMessagesForUser);

			preparedStatement.setInt(1, userId);

			ResultSet resultSet = preparedStatement.executeQuery();

			List<MessagePojo> messagesForUser = new ArrayList<>();

			while (resultSet.next()) {
				MessagePojo message = new MessagePojo(resultSet.getString("title"), resultSet.getString("body"),
						resultSet.getTimestamp("sent_at"), resultSet.getInt("sender_id"));
				messagesForUser.add(message);
			}

			if (messagesForUser.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Not Found: No messages found for the user.").build();
			}

			return Response.ok(messagesForUser).build();

		} catch (SQLException e) {

			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Internal Server Error: Error retrieving messages for the user.").build();
		}
	}
}
