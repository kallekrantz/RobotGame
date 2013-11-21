package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.Robot;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/{userid}/robot/{robotid}")
public class RobotIdResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") int userid, @PathParam("robotid") int robotid) {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory;
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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") int userid, @PathParam("robotid") int robotid, JSONObject jsonObj) throws Exception {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory;
        Robot r = null;
        try {
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            r = (Robot) session.createQuery("from Robot r where r.user.id = :userid and r.id = :robotid").setInteger("userid", userid).setInteger("robotid", robotid).uniqueResult();
            if(r == null){
                throw new NotFoundException();
            }
            if (jsonObj.has("robotDesign")) {
                r.setRobotDesign(jsonObj.getString("robotDesign"));
            }
            if(jsonObj.has("robotName")){
                r.setRobotName(jsonObj.getString("robotName"));
            }
            r = (Robot) session.merge(r);
            session.saveOrUpdate(r);
            session.flush();
            tx.commit();
        }
        catch (WebApplicationException we){
            we.printStackTrace();
            tx.rollback();
        }
        catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return Response.serverError().build();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Response.ok(r).build();
    }
}