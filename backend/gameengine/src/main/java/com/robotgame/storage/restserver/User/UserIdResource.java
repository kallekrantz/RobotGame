package com.robotgame.storage.restserver.User;

import com.robotgame.storage.entities.User;
import com.robotgame.storage.restserver.exceptions.NotFoundException;
import com.robotgame.storage.services.UserService;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Resource that manages all UserEndpoints that require an id.
 */
@Path("/user/{userid}")
public class UserIdResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final String userVar) {
        UserService service = new UserService();
        User u;
        try
        {
            u = service.getUser(Integer.parseInt(userVar));
        }
        catch (NumberFormatException nfe)
        {
            u = service.getUser(userVar);
        }
        if(u == null){
            throw new NotFoundException();
        }
        return Response.ok(u).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") final String userVar, final JSONObject jsonObj){
        UserService service = new UserService();
        User u;
        try
        {
            u = service.getUser(Integer.parseInt(userVar));
        }
        catch (NumberFormatException nfe)
        {
            u = service.getUser(userVar);
        }
        if(u == null){
            throw new NotFoundException();
        }
        return Response.ok(u).build();
    }
}
