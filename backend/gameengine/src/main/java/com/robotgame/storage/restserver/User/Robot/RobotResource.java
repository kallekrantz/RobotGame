package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.entities.RobotEntity;
import com.robotgame.storage.entities.User;
import com.robotgame.storage.services.RobotService;
import org.codehaus.jettison.json.JSONObject;
import com.robotgame.storage.restserver.exceptions.NotFoundException;


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
    public Response get(@PathParam("userid") final String userVar){
        RobotService service = new RobotService();
        List<RobotEntity> robotEntityList;
        try
        {
            robotEntityList = service.getAllRobots(Integer.parseInt(userVar));
        }
        catch (NumberFormatException nfe)
        {
            robotEntityList = service.getAllRobots(userVar);
        }
        return Response.ok(robotEntityList.toArray(new RobotEntity[robotEntityList.size()])).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@PathParam("userid") final String userVar, JSONObject jsonObj){
        RobotService service = new RobotService();
        RobotEntity r;
        try{
            r = service.createRobot(Integer.parseInt(userVar), jsonObj);
        }
        catch(NumberFormatException nfe){
            r = service.createRobot(userVar, jsonObj);
        }
        return Response.ok(r).build();
    }

}
