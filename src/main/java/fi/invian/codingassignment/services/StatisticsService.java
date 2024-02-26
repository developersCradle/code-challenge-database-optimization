package fi.invian.codingassignment.services;

import java.sql.SQLException;

import fi.invian.codingassignment.pojos.HelloPojo;

public interface HelloService {
	HelloPojo getMessage() throws SQLException;
}
