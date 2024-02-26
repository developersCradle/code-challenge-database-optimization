package fi.invian.codingassignment.services;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.pojos.MessagePojo;
import fi.invian.codingassignment.pojos.UserPojo;

public interface MessageService {
	Response sendNewMessage(MessagePojo message) throws SQLException;
	//HelloPojo getMessages(recipientUser) throws SQLException;
}


