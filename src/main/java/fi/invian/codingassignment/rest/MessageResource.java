package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.services.MessageService;
import fi.invian.codingassignment.services.StatisticsService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Path("/messages")
public class MessageResource {

	@Inject
	private MessageService messageService;
	
	
    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendNewMessage(String newMessage) throws SQLException {
    	
    	MessagePojo message = new MessagePojo(); 
    	message.setMessagePojoId(1);
    	message.setBody("Some Body");
    	message.setTitle("Some Title");
//    	message.setSentAt(new Timestamp(System.currentTimeMillis())); Time should come from Db when inserted
    			
    	return messageService.sendNewMessage(message);
    }
    
    

//    @GET
//    @Path("/{userId}/inbox")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String readMessagesForUser(@PathParam("userId") String userId) {
//    	
//    	
//    	
//    	 String resultMessage = "Message sent successfully";
//         return Response.ok(resultMessage, MediaType.TEXT_PLAIN).build();
//           
//    }
//    
    
    
}
