package fi.invian.codingassignment.services;

import java.sql.SQLException;
import java.util.List;

import fi.invian.codingassignment.pojos.UserPojo;

public interface StatisticsService {
	List<UserPojo> getTopTenUsers() throws SQLException;

}
