package com.robotgame.storage.restserver;

/*import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.User;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;*/

import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 */
@Path("/user")
public class UserResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response post() {


        Session session = SessionCreator.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        User user = new User("a","b", "c",1,"asd");
        session.save(user);
        List<User> userList = session.createQuery("from User").list();

        for(User u : userList) {
            System.out.println(user.getFirstname());
        }
        return Response.ok("hej").build();
    }
}

