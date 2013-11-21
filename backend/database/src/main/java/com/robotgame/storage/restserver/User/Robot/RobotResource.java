package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.Robot;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
    public Response getList(@PathParam("userid") final int userid){
        List<Robot> robotList = (List<Robot>) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                return session.createQuery("select distinct r from Robot r where r.user.id = :userid").setInteger("userid", userid).list();
            }
        });
        return Response.ok(robotList.toArray(new Robot[robotList.size()])).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(JSONObject jsonObj, @PathParam("userid") final int userid){
        final Robot r;
        try{
            r = Robot.create(jsonObj);
        }catch(JSONException e){
            throw new BadRequestException();
        }
        Robot outRobot = (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                User u = (User) session.get(User.class, userid);
                r.setUser(u);
                return session.save(r);
            }
        });
        return Response.ok(outRobot).build();
    }

}
