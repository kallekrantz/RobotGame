package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.Robot;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 08/11/13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */

@Path("user/{userid}/robot")
public class RobotResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@PathParam("userid") int userid){
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        List<Robot> robotList = null;
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            robotList = session.createQuery("select distinct r from Robot r where r.user.id = :userid").setInteger("userid", userid).list();
            session.flush();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return Response.ok(robotList.toArray(new Robot[robotList.size()])).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(JSONObject jsonObj, @PathParam("userid") int userid){
        Session session = null;
        Transaction tx = null;
        Robot r = new Robot();

        try{
            r.merge(jsonObj);
        }catch(JSONException e){
            throw new BadRequestException();
        }

        try{
            session = SessionCreator.getSessionFactory().openSession();
            tx = session.beginTransaction();
            User u = (User) session.get(User.class, userid);
            r.setUser(u);
            session.save(r);
            session.flush();
            tx.commit();
            return Response.ok(r).build();
        }catch(WebApplicationException ex){
            ex.printStackTrace();
            tx.rollback();
            throw ex;
        }catch(Exception e){
            e.printStackTrace();
            tx.rollback();
            throw new InternalServerErrorException();
        }finally{
            if(session != null){
                session.close();
            }
        }
    }

}
