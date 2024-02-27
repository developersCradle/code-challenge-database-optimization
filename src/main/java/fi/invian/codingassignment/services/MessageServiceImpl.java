package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.MessagePojo;

public class MessageServiceImpl implements MessageService {

	@Override
	public Response sendNewMessage(MessagePojo message) throws SQLException {

		System.out.println("Sending new message " + message);

		try (Connection connection = DatabaseConnection.getConnection()) {

			String sqlForMessagesTable = "INSERT INTO Messages (title, body, sender_id) VALUES (?, ?, ?)"; // For

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
																// important

					for (Integer receaver_id : message.getReceiverIds()) {

						String sqlForRecipientsTable = "INSERT INTO Recipients (message_id, receiver_id) VALUES (?, ?)"; // For
																															// Recipients
						System.out.println("message_id is: " + message_id);
						System.out.println("receaver_id is: " + receaver_id);

						PreparedStatement preparedStatement = connection.prepareStatement(sqlForRecipientsTable);
						preparedStatement.setInt(1, message_id);
						preparedStatement.setInt(2, receaver_id);
						int executeUpdate = preparedStatement.executeUpdate();

						System.out.println("Insert was successful " + executeUpdate);
					}
				} else {
					// GeneratedKeys not supported by database.
					// Other logic to get inserted Message row id from db
					// Rollback here or something. TODO
					throw new SQLException("No generated keys received. Abort Actions");

				}

				System.out.println("Message created");
				return Response.status(Response.Status.CREATED).entity("Created: The message was sent successfully.").build();
			} else {

				return Response.status(Response.Status.CONFLICT)
						.entity("Conflict: The record conflicting with record on database").build();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Conflict: Unique constraint violation or SQL error").build();
		}
	}

	@Override
	public Response getMessagesForAdressedUser(int userId) throws SQLException {

		String sqlForGettingMessagesForUser = "SELECT * FROM Messages JOIN Recipients ON Messages.message_id = Recipients.message_id  WHERE Recipients.receiver_id = ?";

		try (Connection connection = DatabaseConnection.getConnection()) {

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
				return Response.status(Response.Status.NOT_FOUND).entity("No messages found for the user.").build();
			}
			
			return Response.ok(messagesForUser).build();
			
		} catch (SQLException e) {

			e.printStackTrace();

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Error retrieving messages for the user.").build();
		}
	}

}
