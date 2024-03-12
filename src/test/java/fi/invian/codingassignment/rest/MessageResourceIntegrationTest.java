package fi.invian.codingassignment.rest;

import java.util.Arrays;

import org.junit.Test;

import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.services.MessageService;
import fi.invian.codingassignment.services.MessageServiceImpl;
import fi.invian.codingassignment.services.StatisticsService;
import fi.invian.codingassignment.services.StatisticsServiceImpl;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;



/*
 * Integration tests for MessageResource
 */
public class MessageResourceIntegrationTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig().packages("fi.invian.codingassignment.rest").register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(StatisticsServiceImpl.class).to(StatisticsService.class);
				bind(MessageServiceImpl.class).to(MessageService.class);
			}
		});
	}

	@Test
	public void testSendNewMessage() {

		// Given - START
		MessagePojo testMessage = new MessagePojo();
		testMessage.setMessageId(1);
		testMessage.setTitle("Test Title");
		testMessage.setBody("Test Body");
//		testMessage.setSentAt(new Date());
		testMessage.setSenderId(123);
		testMessage.setReceiverIds(Arrays.asList(456, 789));
		// Given - END

		// When - START
		Response response = target("/messages/send").request()
				.post(Entity.entity(testMessage, MediaType.APPLICATION_JSON));
		// When - END

		// Then - START
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Created: The message was sent successfully.", response.readEntity(String.class));
		// Then - END
	}

}
