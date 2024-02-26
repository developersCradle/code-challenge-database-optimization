package fi.invian.codingassignment.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fi.invian.codingassignment.pojos.UserPojo;

public class StatisticsServiceImpl implements StatisticsService{


	@Override
	public List<UserPojo> getTopTenUsers() throws SQLException {
		
		
		System.out.println("Getting top 10 users");

    	List<UserPojo> top10Users = new ArrayList<UserPojo>();
    	
    	top10Users.add(new UserPojo("User 1"));
    	top10Users.add(new UserPojo("User 2"));
    	top10Users.add(new UserPojo("User 3"));
    	top10Users.add(new UserPojo("User 4"));
    	top10Users.add(new UserPojo("User 5"));
    	top10Users.add(new UserPojo("User 6"));
    	top10Users.add(new UserPojo("User 7"));
    	top10Users.add(new UserPojo("User 8"));
    	top10Users.add(new UserPojo("User 9"));
    	top10Users.add(new UserPojo("User 10"));

		return top10Users;
//		try (
//	            Connection c = DatabaseConnection.getConnection();
//	            PreparedStatement p = c.prepareStatement("SELECT hello_message FROM example_table")
//	        ) {
//	            ResultSet r = p.executeQuery();
//	            if (r.next()) {
//	                return new HelloPojo(r.getString(1));
//	            } else {
//	                throw new NotFoundException("Database did not contain the expected message.");
//	            }
////	        }
	}

}
