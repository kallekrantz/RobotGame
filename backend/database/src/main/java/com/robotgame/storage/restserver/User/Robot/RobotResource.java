package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.entities.Robot;
import com.robotgame.storage.services.RobotService;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 08/11/13
 * Time: 15:21
 * To change this template use File | Settings | File Templates.
 */

@Path("user/{userid}/robot")
public class RobotResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final int userid){
        RobotService service = new RobotService();
        List<Robot> robotList = service.getAllRobots(userid);
        if(robotList == null){
            throw new NotFoundException();
        }
        return Response.ok(robotList.toArray(new Robot[robotList.size()])).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@PathParam("userid") final int userid, JSONObject jsonObj){
        RobotService service = new RobotService();
        Robot r = service.createRobot(userid, jsonObj);
        return Response.ok(r).build();
    }

}
