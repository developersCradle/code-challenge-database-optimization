package fi.invian;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

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
 * TODO HEIKKI(Testing, Software quality) Connect or add data before testing. Test works, but need to insert data to container
 */
public class AppTest extends JerseyTest {

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
		// When - START

		// Then - START
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("Created: The message was sent successfully.", response.readEntity(String.class));
		// Then - END
	}

}
