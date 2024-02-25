package fi.invian.codingassignment.app;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fi.invian.codingassignment.services.HelloService;
import fi.invian.codingassignment.services.HelloServiceImpl;

public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(HelloServiceImpl.class).to(HelloService.class);
	}

}
