package com.robotgame.storage.restserver.User.Auth;

import com.robotgame.storage.database.PasswordHasher;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.AuthToken;
import com.robotgame.storage.entities.User;
import com.robotgame.storage.restserver.exceptions.UnauthorizedException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 11/6/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/user/{userid}/auth")
public class AuthResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(@PathParam("userid") int userid, String jsonstr) throws Exception {
        Session session = null;
        Transaction tx = null;
        SessionFactory sessionFactory = null;

        JSONObject jsonObj = new JSONObject(jsonstr);

        try{
            sessionFactory = SessionCreator.getSessionFactory();
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, userid);
            if(user.getPwdHash().equals(PasswordHasher.hash((String) jsonObj.get("password")))){
                AuthToken token = new AuthToken(user);
                session.save(token);
                session.flush();
                tx.commit();
                return Response.ok(token).build();
            }else{
                throw new UnauthorizedException();
            }
        }
        catch(WebApplicationException e){
            e.printStackTrace();
            tx.rollback();
            throw e;
        }
        catch(Exception e){
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
