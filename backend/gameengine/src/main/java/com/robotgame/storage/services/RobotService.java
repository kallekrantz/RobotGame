package com.robotgame.storage.services;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.entities.RobotEntity;
import com.robotgame.storage.entities.User;
import com.robotgame.storage.restserver.exceptions.BadRequestException;
import com.robotgame.storage.restserver.exceptions.NotFoundException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.List;

/**
 * RobotService for rest endpoint -> database. Currently maps to Hibernate.
 */
public class RobotService {



    public List<RobotEntity> getAllRobots(final int userId){
        return (List<RobotEntity>) DatabaseUtil.runRequest(new DatabaseRequest() {
           
            public Object request(Session session) {
                return session.createQuery("select r from com.robotgame.storage.entities.RobotEntity r where r.user.id = :userid").setInteger("userid", userId).list();
            }
        });
    }
    public List<RobotEntity> getAllRobots(final String username){
        return (List<RobotEntity>) DatabaseUtil.runRequest(new DatabaseRequest() {

            public Object request(Session session) {
                return session.createQuery("select r from com.robotgame.storage.entities.RobotEntity r where r.user.username = :username").setString("username", username).list();
            }
        });
    }

    public RobotEntity getRobot(final int userId, final int robotId){
        RobotEntity r =  (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {
           
            public Object request(Session session) {
                RobotEntity r = (RobotEntity) session.createQuery("from com.robotgame.storage.entities.RobotEntity r where r.user.id = :userid and r.id = :robotid")
                        .setInteger("userid", userId)
                        .setInteger("robotid", robotId)
                        .uniqueResult();
                return r;
            }
        });
        if(r == null){
            throw new NotFoundException();
        }
        return r;
    }
    public RobotEntity getRobot(final String username, final int robotId){
        RobotEntity r = (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {

            public Object request(Session session) {
                RobotEntity r = (RobotEntity) session.createQuery("from com.robotgame.storage.entities.RobotEntity r where r.user.username = :username and r.id = :robotid")
                        .setString("username", username)
                        .setInteger("robotid", robotId)
                        .uniqueResult();
                return r;
            }
        });
        if(r == null){
            throw new NotFoundException();
        }
        return r;
    }
    public RobotEntity editRobot(final int userId, final int robotId, final JSONObject jsonObj){
        RobotEntity r = getRobot(userId, robotId);
        return editRobot(r, jsonObj);
    }
    public RobotEntity editRobot(final String username, final int robotId, final JSONObject jsonObj){
        RobotEntity r = getRobot(username, robotId);
        return editRobot(r, jsonObj);
    }

    public RobotEntity createRobot(final int userId, final JSONObject jsonObj){
        UserService usr = new UserService();
        User u = usr.getUser(userId);
        return createRobot(u, jsonObj);
    }

    public RobotEntity createRobot(final String username, final JSONObject jsonObj){
        UserService usr = new UserService();
        User u = usr.getUser(username);
        return createRobot(u, jsonObj);
    }

    private RobotEntity editRobot(RobotEntity r, final JSONObject jsonObj){
        try {
            r = RobotEntity.merge(r, jsonObj);
        } catch (JSONException e1) {
            throw new BadRequestException();
        }
        final RobotEntity rb = r;
        return (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {

            public Object request(Session session) {
                RobotEntity r = rb;
                r = (RobotEntity) session.merge(r);
                session.saveOrUpdate(r);
                return r;
            }
        });
    }
    private RobotEntity createRobot(final User u, final JSONObject jsonObj){
        final RobotEntity r;
        try{
            r = RobotEntity.create(jsonObj);
        }catch(JSONException e){
            throw new BadRequestException();
        }
        return (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {

            public Object request(Session session) {
                r.setUser(u);
                session.saveOrUpdate(r);
                return r;
            }
        });
    }
}
