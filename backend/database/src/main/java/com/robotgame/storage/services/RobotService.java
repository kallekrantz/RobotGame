package com.robotgame.storage.services;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.entities.Robot;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * RobotService for rest endpoint -> database. Currently maps to Hibernate.
 */
public class RobotService {



    public List<Robot> getAllRobots(final int userId){
        return (List<Robot>) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                return session.createQuery("select distinct r from Robot r where r.user.id = :userid").setInteger("userid", userId).list();
            }
        });
    }
    public Robot getRobot(final int userId, final int robotId){
        return (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                Robot r = (Robot) session.createQuery("from Robot r where r.user.id = :userid and r.id = :robotid")
                        .setInteger("userid", userId)
                        .setInteger("robotid", robotId)
                        .uniqueResult();
                return r;
            }
        });
    }
    public Robot editRobot(final int userId, final int robotId, final JSONObject jsonObj){
        return (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                Robot r = getRobot(userId, robotId);
                if(r == null){
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

    }
    public Robot createRobot(final int userId, final JSONObject jsonObj){
        final Robot r;
        try{
            r = Robot.create(jsonObj);
        }catch(JSONException e){
            throw new BadRequestException();
        }
        return (Robot) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                User u = (User) session.get(User.class, userId);
                r.setUser(u);
                return session.save(r);
            }
        });
    }
}
