package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.UserPojo;
import fi.invian.codingassignment.services.StatisticsService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<UserPojo> getTopUsersLast30DaysByMessageCount() {
    	
    	return Collections.<UserPojo>emptyList();  //For now empty one
    	
//    	return statisticsServce.getTopTenUsers(); Todo
    }
}
