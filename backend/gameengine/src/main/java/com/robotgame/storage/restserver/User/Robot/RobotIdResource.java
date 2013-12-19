package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.entities.RobotEntity;
import com.robotgame.storage.entities.User;
import com.robotgame.storage.services.RobotService;
import org.codehaus.jettison.json.JSONObject;
import com.robotgame.storage.restserver.exceptions.NotFoundException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource that manages all RobotEndpoints that require an id.
 */
@Path("user/{userid}/robot/{robotid}")
public class RobotIdResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final String userVar, @PathParam("robotid") final int robotid) {
        RobotService service = new RobotService();
        RobotEntity r;
        try
        {
            r = service.getRobot(Integer.parseInt(userVar), robotid);
        }
        catch (NumberFormatException nfe)
        {
            r = service.getRobot(userVar, robotid);
        }
        return Response.ok(r).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") final String userVar, @PathParam("robotid") final int robotid, final JSONObject jsonObj){
        RobotService service = new RobotService();
        RobotEntity r;
        try
        {
            r = service.editRobot(Integer.parseInt(userVar), robotid, jsonObj);
        }
        catch (NumberFormatException nfe)
        {
            r = service.editRobot(userVar, robotid, jsonObj);
        }
        return Response.ok(r).build();
    }
}