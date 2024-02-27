package fi.invian.codingassignment.rest;

import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.services.MessageService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/messages")
public class MessageResource {

	@Inject
	private MessageService messageService;
	
    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendNewMessage() throws SQLException {
    	
    	MessagePojo message = new MessagePojo(); 
    	message.setSenderId(1); // Sender in system
    	message.setBody("Some Body");
    	message.setTitle("Some Title");
    	
    	List<Integer> receaverIds = new ArrayList();
    	receaverIds.add(new Integer(1)); // Setting 5 Receavers for now
    	receaverIds.add(new Integer(2));
    	receaverIds.add(new Integer(3));
    	receaverIds.add(new Integer(4));
    	receaverIds.add(new Integer(5));
    	
    	message.setReceiverIds(receaverIds);
//    	message.setSentAt(new Timestamp(System.currentTimeMillis())); Time should come from Db when inserted
    			
    	return messageService.sendNewMessage(message);
    }
    
    

    @GET
    @Path("/{userId}/inbox")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readMessagesForUser(@PathParam("userId") String userId) throws SQLException {
    	
    	 
    	return messageService.getMessagesForAdressedUser(1);
           
    }
    
    
    
}
