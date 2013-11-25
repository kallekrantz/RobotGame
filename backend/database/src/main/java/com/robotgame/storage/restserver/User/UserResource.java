package com.robotgame.storage.restserver.User;

import com.robotgame.storage.entities.User;
import com.robotgame.storage.services.UserService;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Endpoint that manages all users. Including creating a single one.
 */

@Path("/user")
public class UserResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(JSONObject jsonObj){
        UserService service = new UserService();
        User u = service.createUser(jsonObj);
        if(u == null){
            throw new NotFoundException();
        }
        return Response.ok(u).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        UserService service = new UserService();
        List<User> userList = service.getAllUsers();
        if(userList == null){
            throw new NotFoundException();
        }
        return Response.ok(userList.toArray(new User[userList.size()])).build();
    }
}

