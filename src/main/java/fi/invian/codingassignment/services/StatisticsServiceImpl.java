package fi.invian.codingassignment.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.invian.codingassignment.app.DatabaseConnection;
import fi.invian.codingassignment.pojos.UserPojo;

public class StatisticsServiceImpl implements StatisticsService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	
//	Statistics endpoint: Top 10 users (by sent message count) sorted by decreasing sent
//	message count for the last 30 days
	@Override
	public Response getTopUsersWithMessageCount(int numberOfUsers, int daysAgo, String sortDirection) throws SQLException {

		logger.info("Getting top " + numberOfUsers + " users. From " + daysAgo + " ago, with " + sortDirection );
		
	    String sqlForGettingTopUsersWithMessageCount = "SELECT sender_id as sending_user, COUNT(*) as message_count " +
	                "FROM Messages " +
	                "WHERE sent_at >= CURDATE() - INTERVAL ? DAY " +
	                "GROUP BY sender_id " +
	                "ORDER BY message_count " + sortDirection + " " + // Add the sorting direction dynamically
	                "LIMIT ?;";
	  	
	    try (Connection connection = DatabaseConnection.getConnection()) {
	  		
	  		PreparedStatement preparedStatement = connection.prepareStatement(sqlForGettingTopUsersWithMessageCount);

			preparedStatement.setInt(1, daysAgo);
			preparedStatement.setInt(2, numberOfUsers);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<UserPojo> users = new ArrayList<>();
			
			while (resultSet.next()) {
				UserPojo user = new UserPojo(resultSet.getInt("sending_user"), resultSet.getInt("message_count"));
				users.add(user);
			}

			if (users.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity("Not Found: No top users were found.").build();
			}
			
			return Response.ok(users).build();
		}
		catch (Exception e) {
			
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error: Internal server error").build();
        }
	}
}
