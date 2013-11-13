package com.robotgame.storage.restserver.User;

import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public Response get(@PathParam("userid") int userid) {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory;
        User u = null;
        try {
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            u = (User) session.createQuery("from Robot r where r.user.id = :userid").setInteger("userid", userid).uniqueResult();
            session.flush();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Response.ok(u).build();
    }
}
