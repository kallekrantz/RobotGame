package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.entities.RobotEntity;
import com.robotgame.storage.services.RobotService;
import org.codehaus.jettison.json.JSONObject;

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
    public Response get(@PathParam("userid") final int userid, @PathParam("robotid") final int robotid) {
        RobotService service = new RobotService();
        RobotEntity r = service.getRobot(userid, robotid);
        if(r == null){
            throw new NotFoundException();
        }
        return Response.ok(r).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") final int userid, @PathParam("robotid") final int robotid, final JSONObject jsonObj){
        RobotService service = new RobotService();
        RobotEntity r = service.editRobot(userid, robotid, jsonObj);
        return Response.ok(r).build();
    }
}