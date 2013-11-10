package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.Robot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/{userid}/robot/{robotid}")
public class RobotIdResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") int userid, @PathParam("robotid") int robotid) {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        Robot r = null;
        try {
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            r = (Robot) session.createQuery("from Robot r where r.user.id = :userid and r.id = :robotid").setInteger("userid", userid).setInteger("robotid", robotid).uniqueResult();
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
        return Response.ok(r).build();
    }
}