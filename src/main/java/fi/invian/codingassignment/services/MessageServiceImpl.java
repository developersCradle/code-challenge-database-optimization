package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.pojos.UserPojo;

public class MessageServiceImpl implements MessageService {

	@Override
	public Response sendNewMessage(MessagePojo message) throws SQLException {

		System.out.println("Sending new message" + message);
		return Response.status(Response.Status.CREATED).entity("Created:").build(); //Return this for now, focus on db design
		
//		String sql = "INSERT INTO Messages (title, body, sender_id) VALUES (?, ?, ?)";
//
//		try (Connection connection = DatabaseConnection.getConnection()) {
//
//			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setString(1, message.getTitle());
//	        preparedStatement.setString(2, message.getBody());
//	        preparedStatement.setInt(3, message.getSenderId());
//
//
//			int rowsAffected = preparedStatement.executeUpdate();
//
//			if (rowsAffected > 0) {
//				
//				return Response.status(Response.Status.CREATED).entity("Created:").build();
//			} else {
//				
//				return Response.status(Response.Status.CONFLICT)
//						.entity("Conflict: The record conflicting with record on database").build();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
//			return Response.status(Response.Status.CONFLICT)
//					.entity("Conflict: Unique constraint violation or SQL error").build();
//		}
	}

}
