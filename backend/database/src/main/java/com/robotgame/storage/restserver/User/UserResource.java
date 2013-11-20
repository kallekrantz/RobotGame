package com.robotgame.storage.restserver.User;

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
    public Response post(JSONObject jsonObj){
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        Response.ResponseBuilder response;
        User merged;
        User u = new User();
        try {
            u = u.merge(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new BadRequestException();
        }
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            merged = (User) session.merge(u);
            session.saveOrUpdate(merged);
            session.flush();
            tx.commit();
            response = Response.ok(merged);
        }
        catch(ServerErrorException ex){
            ex.printStackTrace();
            tx.rollback();
            throw ex;
        }
        catch(Exception e){
            e.printStackTrace();
            tx.rollback();
            throw new InternalServerErrorException();
        }
        finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
        return response.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;
        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<User> userList = session.createQuery("from User").list();
            session.flush();
            tx.commit();
            return Response.ok(userList.toArray(new User[userList.size()])).build();
        }
        catch(WebApplicationException ex){
            ex.printStackTrace();
            tx.rollback();
            throw ex;
        }
        catch(Exception e){
            e.printStackTrace();
            tx.rollback();
            throw new InternalServerErrorException();
        }
        finally{
            if(session != null){
                session.close();
            }
        }
    }
}

