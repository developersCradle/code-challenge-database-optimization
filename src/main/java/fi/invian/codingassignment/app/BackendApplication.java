package fi.invian.codingassignment.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import fi.invian.codingassignment.services.MessageService;
import fi.invian.codingassignment.services.MessageServiceImpl;
import fi.invian.codingassignment.services.StatisticsService;
import fi.invian.codingassignment.services.StatisticsServiceImpl;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/*
 * TODO HEIKKI(Cache, Optimization) Some cache would be mby good, since app don't have much mutation operations. Could query old ones forom cache
 */
public class BackendApplication {
    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://127.0.0.1/").port(8080).build();
        Server server = JettyHttpContainerFactory.createServer(baseUri, false);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/*");
		ServletContainer jersey = new ServletContainer(new ResourceConfig() {
			{
				register(new AbstractBinder() {
					@Override
					protected void configure() {
						bind(StatisticsServiceImpl.class).to(StatisticsService.class);
						bind(MessageServiceImpl.class).to(MessageService.class);
					}
				});

				packages("fi.invian.codingassignment.rest");
			}
		});

        ServletHolder holder = new ServletHolder(jersey);
        context.addServlet(holder, "/*");
        server.setHandler(context);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeDataSource();
            try {
                server.stop();
            } catch (Exception e) {
                // ignored
            }
        }));
        
        server.start();
    
    }
}
