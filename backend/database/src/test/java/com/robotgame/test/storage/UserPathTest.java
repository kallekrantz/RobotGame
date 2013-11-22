package com.robotgame.test.storage;

import com.robotgame.storage.Main;
import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.database.SessionCreator;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserPathTest {

    private HttpServer server;
    private WebResource webResource;

    @Before
    public void setUp() throws Exception {
        Configuration config = new Configuration();
        config.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        config.setProperty("hibernate.connection.url",
                "jdbc:h2:mem");
        config.setProperty("hibernate.hbm2ddl.auto",
                "create");
        // start the server
        server = Main.startServer(config);
        // create the client
        Client c = Client.create();
        webResource = c.create().resource(Main.BASE_URI);
        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                session.createQuery("delete from Users");
                return null;
            }
        });
        SessionCreator.getSessionFactory().close();
    }

    /**
     * Tests whether a user can be added.
     */
    @Test
    public void testAddUser() throws JSONException {
        JSONObject obj = new JSONObject("{username:'krantz', firstname:'kalle', lastname:'krantz', password:'fisk'}");
        ClientResponse r;

        r = webResource.path("user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, obj);
        assertThat(r.getStatus(), is(200));

        r = webResource.path("user").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(r.getStatus(), is(200));

    }

    @Test
    public void testGetNonExistingUser() throws JSONException {
        ClientResponse r = webResource.path("user/0").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertThat(r.getStatus(), is(404));
    }
    @Test
    public void testPutExistingUser() throws JSONException{

    }
}
