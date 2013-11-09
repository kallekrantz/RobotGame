package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.PasswordHasher;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.AuthToken;
import com.robotgame.storage.entities.Robot;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
    public Response get(@PathParam("userid") int userid){
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        List<Robot> robotList = null;
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            robotList = session.createQuery("from Robot r where r.user.userId = :userid").setInteger("userid", userid).list();
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
    public Response post(String jsonstr, @PathParam("userid") int userid) throws JSONException {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        JSONObject jsonObj = new JSONObject(jsonstr);
        Robot r = new Robot();
        r.setRobotDesign((String)jsonObj.get("robotDesign"));
        r.setRobotName((String)jsonObj.get("robotName"));
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            User u = (User) session.get(User.class, userid);
            r.setUser(u);
            session.save(r);
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
        return Response.ok(r).build();
    }
}
