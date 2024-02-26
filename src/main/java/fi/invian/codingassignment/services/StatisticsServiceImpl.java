package fi.invian.codingassignment.services;

import java.sql.SQLException;

import fi.invian.codingassignment.pojos.HelloPojo;

public class HelloServiceImpl implements HelloService{

	@Override
	public HelloPojo getMessage() throws SQLException {
		
		System.out.println("Getting message");
		return new HelloPojo("Getting message");
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
//	        }
	}

}
