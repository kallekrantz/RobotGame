package com.robotgame.storage.restserver.Path.User.Robot;

import com.robotgame.storage.entities.RobotEntity;
import com.robotgame.storage.services.RobotService;
import com.sun.jersey.api.NotFoundException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Endpoint that manages all robots. Including creating a single one.
 */

@Path("user/{userid}/robot")
public class RobotResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final int userid){
        RobotService service = new RobotService();
        List<RobotEntity> robotEntityList = service.getAllRobots(userid);
        if(robotEntityList == null){
            throw new NotFoundException();
        }
        return Response.ok(robotEntityList.toArray(new RobotEntity[robotEntityList.size()])).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@PathParam("userid") final int userid, JSONObject jsonObj){
        RobotService service = new RobotService();
        RobotEntity r = service.createRobot(userid, jsonObj);
        return Response.ok(r).build();
    }

}
