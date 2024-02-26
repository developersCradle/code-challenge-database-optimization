package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.UserPojo;

public class StatisticsServiceImpl implements StatisticsService {

	@Override
	public Response getTopUsersWithMessageCount(int numberOfUsers, int daysAgo, String sort) throws SQLException {

		System.out.println("Getting top" + numberOfUsers + "users. From " + daysAgo + "ago, with " + sort );

		String sql = "SELECT * FROM Users"; //This will change, return top 10 users

		try (Connection connection = DatabaseConnection.getConnection()) {
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<UserPojo> users = new ArrayList<>();
			while (resultSet.next()) {
				UserPojo user = new UserPojo(resultSet.getInt("user_id"), resultSet.getString("name"));
				users.add(user);
			}

			return Response.ok(users).build();
		}
		catch (Exception e) {
			
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
	}
}
