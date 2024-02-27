package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.MessagePojo;

public class MessageServiceImpl implements MessageService {

	

	@Override
	public Response sendNewMessage(MessagePojo message) throws SQLException {

		System.out.println("Sending new message " + message);

		try (Connection connection = DatabaseConnection.getConnection()) {

			String sqlforMessagesTable = "INSERT INTO Messages (title, body, sender_id) VALUES (?, ?, ?)"; // For
																											// Messages
																											// table

			PreparedStatement preparedStatementForMessages = connection.prepareStatement(sqlforMessagesTable,
					Statement.RETURN_GENERATED_KEYS); // We want to use RETURN_GENERATED_KEYS. Inserted id will return from
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
						
						String sqlforRecipientsTable = "INSERT INTO Recipients (message_id, receiver_id) VALUES (?, ?)"; // For Recipients table


						System.out.println("message_id is: " + message_id);
						System.out.println("receaver_id is: " + receaver_id);

						
						PreparedStatement preparedStatement = connection.prepareStatement(sqlforRecipientsTable);
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
				return Response.status(Response.Status.CREATED).entity("Created:").build();
			} else {

				return Response.status(Response.Status.CONFLICT)
						.entity("Conflict: The record conflicting with record on database").build();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return Response.status(Response.Status.CONFLICT)
					.entity("Conflict: Unique constraint violation or SQL error").build();
		}
	}

}
