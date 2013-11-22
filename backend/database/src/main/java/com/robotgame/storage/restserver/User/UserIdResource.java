package com.robotgame.storage.restserver.User;

import com.robotgame.storage.entities.User;
import com.robotgame.storage.services.UserService;
import org.codehaus.jettison.json.JSONObject;

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
    public Response get(@PathParam("userid") final int userid) {
        UserService service = new UserService();
        User u = service.getUser(userid);
        if(u == null){
            throw new NotFoundException();
        }
        return Response.ok(u).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") final int userid, final JSONObject jsonObj){
        UserService service = new UserService();
        User u = service.editUser(userid, jsonObj);
        if(u == null){
            throw new NotFoundException();
        }
        return Response.ok(u).build();
    }
}
