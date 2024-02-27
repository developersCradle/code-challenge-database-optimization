package fi.invian.codingassignment.services;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import fi.invian.codingassignment.pojos.MessagePojo;

public interface MessageService {
	Response sendNewMessage(MessagePojo message) throws SQLException;
	Response getMessagesForAdressedUser(int userId) throws SQLException;
}


