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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.MessagePojo;

public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	/*
	 * Send new message
	 */
	@Override
	public Response sendNewMessage(MessagePojo message) throws SQLException {

	
        logger.debug(String.format("Trying to send following message: %s", message));
        
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
					
					for (Integer receiver_id  : message.getReceiverIds()) {

						/*
						 * TODO HEIKKI(Optimization, Query) Check the existence of multiple users in a single query and return the list of valid ones. Should insert to Recipients be done as one big, not individual inserts?
						 * TODO HEIKKI(Bug, Query) When there is no  Receivers, system sends Message  even if there were no receivers
						 */
						if (!userExistsInDb(receiver_id, connection)) {
							logger.warn(String.format("Receiver with user_id %s does not exist. Skipping this user_id for sending message", receiver_id));
							continue;
						}
						
						String sqlForRecipientsTable = "INSERT INTO Recipients (message_id, receiver_id) VALUES (?, ?)"; // For Recipients Table
						
						logger.info(String.format("Sending message with receiver_id is: %s", receiver_id));
						logger.info(String.format("Sending message with message_id is: %s", message_id));

						PreparedStatement preparedStatement = connection.prepareStatement(sqlForRecipientsTable);
						preparedStatement.setInt(1, message_id);
						preparedStatement.setInt(2, receiver_id);
						int executeUpdate = preparedStatement.executeUpdate();

						logger.info(String.format("Insert was %s", (executeUpdate > 0 ? "successful" : "not successful")));
					}
				} else {

					// GeneratedKeys not supported by database.
					// Other logic to get inserted Message row id from db.
					// For now just rollback
					connection.rollback();
					throw new SQLException("No generated keys received. Abort Actions");

				}

				connection.commit();
				
				logger.info("Message created");
				
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
	private boolean userExistsInDb(int user_id, Connection connection) throws SQLException {
		
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
	
	/*
	 * Read messages addressed to a specified user
	 */

	@Override
	public Response getMessagesForAdressedUser(int userId) throws SQLException {
		
		logger.info(String.format("Trying to get messages for %s", userId));
		
		/*
		 * TODO HEIKKI(Optimization, Query) Avoid SELECT * when not necessary. Select only what u need. 
		 */
		String sqlForGettingMessagesForUser = "SELECT * FROM Messages JOIN Recipients ON Messages.message_id = Recipients.message_id  WHERE Recipients.receiver_id = ?";
		//TODO do we need message ID when returning???
		try (Connection connection = DatabaseConnection.getConnection()) {

			if (!userExistsInDb(userId, connection)) { //Nesting connection not recommended, sending connection.
				return Response.status(Response.Status.NOT_FOUND).entity("Not Found: User not found from the system.").build();
			}
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlForGettingMessagesForUser);

			preparedStatement.setInt(1, userId);

			ResultSet resultSet = preparedStatement.executeQuery();

			List<MessagePojo> messagesForUser = new ArrayList<>();

			while (resultSet.next()) {
				MessagePojo message = new MessagePojo();
				
				message.setTitle(resultSet.getString("title"));
				message.setBody(resultSet.getString("body"));
				message.setSenderId(resultSet.getInt("sender_id"));
				message.setSentAt(resultSet.getDate("sent_at"));

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
