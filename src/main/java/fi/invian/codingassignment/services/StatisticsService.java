package fi.invian.codingassignment.services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response;


public interface StatisticsService {
	Response getTopUsersWithMessageCount(int numberOfUsers, int daysAgo, String sort) throws SQLException;
}