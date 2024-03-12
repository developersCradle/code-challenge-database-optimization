package fi.invian.codingassignment.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    StatisticsServiceImpl statisticsServiceMock;

//	@Before
	public void setup() {

//		this.statisticsServiceMock = mock(StatisticsServiceImpl.class);

	}

    
//    @Before
//    public void setUp() {
//    	StatisticsServiceImpl statisticsService = new StatisticsServiceImpl();
//
//    	
//    	
//    }
//    
//    
    @Test
    public void testGetTopUsersWithMessageCount() throws SQLException {
        // Initialize mocks
    	 MockitoAnnotations.initMocks(this);
    	
    	statisticsServiceMock.getTopUsersWithMessageCount(0, 0, null);
//        // Mock input parameters
//        int numberOfUsers = 5;
//        int daysAgo = 7;
//        String sortDirection = "desc";

//        when(resultSet.getInt("sending_user")).thenReturn(1, 2);
//        when(resultSet.getInt("message_count")).thenReturn(10, 8);

        // Set up the mocks for database connection
//        when(databaseConnection.getConnection()).thenReturn(connection);
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        

        // Call the method under test
//        Response response = statisticsService.getTopUsersWithMessageCount(numberOfUsers, daysAgo, sortDirection);

        // Verify any assertions based on the response or other behavior

        // Optionally, verify that certain methods were called
//        verify(databaseConnection).getConnection();
//        verify(connection).prepareStatement(anyString());
//        verify(preparedStatement).setInt(1, daysAgo);
//        verify(preparedStatement).setInt(2, numberOfUsers);
//        verify(preparedStatement).executeQuery();
//        verify(connection).close(); // or mock this behavior if not done inside the method

        // Clean up resources
        // ...

        // Optionally, assert the response or other expectations
        // ...
    }
}