package com.robotgame.storage.restserver.User;

import com.robotgame.storage.entities.User;
import com.robotgame.storage.restserver.exceptions.NotFoundException;
import com.robotgame.storage.restserver.exceptions.NotImplementedException;
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

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("userid") final String userVar){
        UserService service = new UserService();
        boolean result;
        try{
            result = service.deleteUser(Integer.parseInt(userVar));
        }
        catch(NumberFormatException nfe){
            throw new NotImplementedException();
            //result = service.deleteUser(userVar);
        }
        if(!result){
            throw new InternalServerErrorException();
        }
        return Response.noContent().build();
    }
}
