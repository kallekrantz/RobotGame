package com.robotgame.storage.restserver.User;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 10/11/13
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
@Path("/user/{userid}")
public class UserIdResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final int userid) {
        User u = (User) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                return (User) session.createQuery("from User u where u.id = :userid").setInteger("userid", userid).uniqueResult();  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        return Response.ok(u).build();
    }
}
