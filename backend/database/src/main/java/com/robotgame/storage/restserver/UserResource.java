package com.robotgame.storage.restserver;

import com.robotgame.storage.database.PasswordHasher;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(String jsonstr) throws Exception {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;

        JSONObject jsonObj = new JSONObject(jsonstr);
        User u = new User(
                (String)jsonObj.get("username"),
                (String)jsonObj.get("firstname"),
                (String)jsonObj.get("lastname"),
                PasswordHasher.hash((String) jsonObj.get("password"))
        );
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(u);
            session.flush();
            tx.commit();
            return Response.ok(u).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            tx.rollback();
            throw ex;
        }
        finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        List<User> userList = null;
        try{

            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            userList = session.createQuery("from User").list();
        }
        catch(Exception ex){
            ex.printStackTrace();
            tx.rollback();
        }
        finally{
            if(session != null){
                session.close();
            }
            if(sessionFactory != null){
                sessionFactory.close();
            }
        }
        return Response.ok(userList).build();

    }
}

