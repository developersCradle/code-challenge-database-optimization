package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.services.MessageService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.sql.SQLException;

@Path("/messages")
public class MessageResource {

	@Inject
	private MessageService messageService;
	
    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON) //TODO HEIKKI(Documentation, API) Look what decorators should be using.)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendNewMessage(@Valid MessagePojo message) throws SQLException {
//    	message.setSentAt(new Timestamp(System.currentTimeMillis())); Time should come from Db when inserted
    	return messageService.sendNewMessage(message);
    }
    
    

    @GET
    @Path("/{userId}/inbox")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readMessagesForUser(@PathParam("userId") Integer userId) throws SQLException {
    	return messageService.getMessagesForAdressedUser(userId);
    }
    
}
