package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.HelloPojo;
import fi.invian.codingassignment.services.HelloService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloEndpoint {
	
	@Inject
	private HelloService messageService;

	
    @GET
    public HelloPojo getMessage() throws SQLException {
    	return messageService.getMessage();
        
    }
}
