package com.robotgame.storage.restserver.User.Robot;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.Robot;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/{userid}/robot/{robotid}")
public class RobotIdResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("userid") final int userid, @PathParam("robotid") final int robotid) {
        Robot r = (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                return session.createQuery("from Robot r where r.user.id = :userid and r.id = :robotid").setInteger("userid", userid).setInteger("robotid", robotid).uniqueResult();
            }
        });
        return Response.ok(r).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("userid") final int userid, @PathParam("robotid") final int robotid, final JSONObject jsonObj){
        Robot r = (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                Robot r = (Robot) session.createQuery("from Robot r where r.user.id = :userid and r.id = :robotid").setInteger("userid", userid).setInteger("robotid", robotid).uniqueResult();
                if (r == null) {
                    throw new NotFoundException();
                }
                try {
                    Robot.merge(r, jsonObj);
                } catch (JSONException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                r = (Robot) session.merge(r);
                session.saveOrUpdate(r);
                return r;
            }
        });
        return Response.ok(r).build();
    }
}