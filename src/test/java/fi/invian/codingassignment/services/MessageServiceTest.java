//package fi.invian.codingassignment.services;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import fi.invian.codingassignment.services.StatisticsServiceImpl;
//
//public class MessageServiceTest {
//
//    private StatisticsServiceImpl statisticsService;
//
//    @Mock
//    private DatabaseConnection databaseConnection;
//
//    @Mock
//    private Connection connection;
//
//    @Mock
//    private PreparedStatement preparedStatement;
//
//    @Mock
//    private ResultSet resultSet;
//
//    @Before
//    public void setUp() throws SQLException {
//        MockitoAnnotations.initMocks(this);
//        statisticsService = new StatisticsServiceImpl();
//        statisticsService.setDatabaseConnection(databaseConnection);
//
//        // Mock the static method call
//        when(DatabaseConnection.getConnection()).thenReturn(connection);
//
//        // Mock the PreparedStatement and ResultSet
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        // Mock default behavior for ResultSet.next()
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//
//        // Mock the values returned by ResultSet
//        when(resultSet.getInt("sending_user")).thenReturn(1);
//        when(resultSet.getInt("message_count")).thenReturn(10);
//    }
//
//    @Test
//    public void testGetTopUsersWithMessageCount() throws SQLException {
//        int numberOfUsers = 5;
//        int daysAgo = 7;
//        String sortDirection = "desc";
//
//        Response response = statisticsService.getTopUsersWithMessageCount(numberOfUsers, daysAgo, sortDirection);
//
//        // Verify that the database connection and PreparedStatement were created and closed
//        verify(databaseConnection).getConnection();
//        verify(connection).prepareStatement(anyString());
//        verify(preparedStatement).setInt(1, daysAgo);
//        verify(preparedStatement).setInt(2, numberOfUsers);
//        verify(preparedStatement).executeQuery();
//        verify(connection).close();
//
//        // Verify that the expected response is returned
//        assertEquals(Response.ok(new ArrayList<>()).build().getStatus(), response.getStatus());
//    }
//}