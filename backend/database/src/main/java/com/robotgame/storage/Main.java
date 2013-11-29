package com.robotgame.storage;

import com.robotgame.storage.database.SessionCreator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        return startServer(new Configuration().configure());
    }

    /**
     * Starts HTTP Server, SHOULD NOT BE USED FOR PRODUCTION PURPOSES
     * @param hibernateConfig to pass onto Hibernate. Designed for being able to run local temporary database (Junit).
     * @return Grizzly HTTP Server
     */
    public static HttpServer startServer(Configuration hibernateConfig){
        // create a resource config that scans for JAX-RS resources and providers
        // in com.robotgame.storage package
        final ResourceConfig rc = new ResourceConfig().packages("com.robotgame.storage.restserver");


        //Configures SessionCreator for the database sessions.
        //Sort of singleton-dependencyInjection. Could probably be made cleaner.
        SessionCreator.setConfig(hibernateConfig);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method. Starts the HttpServer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

