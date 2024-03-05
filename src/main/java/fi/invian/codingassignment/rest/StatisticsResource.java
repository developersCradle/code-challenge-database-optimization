package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.services.StatisticsService;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/statistics/")
public class StatisticsResource {

	private final StatisticsService statisticsService;
	
	@Inject
	public StatisticsResource(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	
    @GET
    @Path("/top-users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopUsersLast30DaysByMessageCount(
    		@DefaultValue("10") @QueryParam("numberOfUsers") int numberOfUsers,
    		@DefaultValue("30")@QueryParam("daysAgo") int daysAgo,
    		@DefaultValue("desc") @QueryParam("sort") String sort) throws SQLException {
    	
    	return statisticsService.getTopUsersWithMessageCount(numberOfUsers, daysAgo, sort);
    	
    }
}
