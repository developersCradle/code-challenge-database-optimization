package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.UserPojo;
import fi.invian.codingassignment.services.StatisticsService;

import javax.inject.Inject;
import javax.naming.spi.DirStateFactory.Result;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/statistics/")
public class StatisticsResource {

	@Inject
	private StatisticsService statisticsService;

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
