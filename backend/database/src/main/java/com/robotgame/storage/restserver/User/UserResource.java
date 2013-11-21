package com.robotgame.storage.restserver.User;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(JSONObject jsonObj){
        User u;
        try {
            u = User.create(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        final User finalU = u;
        User outUser = (User) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                User merged = (User) session.merge(finalU);
                session.saveOrUpdate(merged);
                return merged;
            }
        });
        return Response.ok(outUser).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<User> userList = (List<User>) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                return session.createQuery("from User").list();
            }
        });
        if(userList == null){
            throw new NotFoundException();
        }
        return Response.ok(userList.toArray(new User[userList.size()])).build();
    }
}

