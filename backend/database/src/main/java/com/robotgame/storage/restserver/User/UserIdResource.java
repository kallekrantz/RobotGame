package com.robotgame.storage.restserver.User;

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
    public Response get(@PathParam("userid") int userid) {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory;
        try {
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            User u = (User) session.createQuery("from User u where u.id = :userid").setInteger("userid", userid).uniqueResult();
            session.flush();
            tx.commit();
            return Response.ok(u).build();
        } catch (WebApplicationException e) {
            e.printStackTrace();
            tx.rollback();
            throw e;
        }catch (Exception ex){
            ex.printStackTrace();
            tx.rollback();
            throw new InternalServerErrorException();
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
