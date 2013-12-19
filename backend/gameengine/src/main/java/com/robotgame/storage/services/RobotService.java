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
    public RobotEntity getRobot(final int userId, final int robotId){
        return (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {
           
            public Object request(Session session) {
                RobotEntity r = (RobotEntity) session.createQuery("from com.robotgame.storage.entities.RobotEntity r where r.user.id = :userid and r.id = :robotid")
                        .setInteger("userid", userId)
                        .setInteger("robotid", robotId)
                        .uniqueResult();
                return r;
            }
        });
    }
    public RobotEntity editRobot(final int userId, final int robotId, final JSONObject jsonObj){
        return (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {
            
            public Object request(Session session) {
                RobotEntity r = getRobot(userId, robotId);
                if(r == null){
                    throw new NotFoundException();
                }
                try {
                    r = RobotEntity.merge(r, jsonObj);
                } catch (JSONException e1) {
                    throw new BadRequestException();
                }
                r = (RobotEntity) session.merge(r);
                session.saveOrUpdate(r);
                return r;
            }
        });

    }
    public RobotEntity createRobot(final int userId, final JSONObject jsonObj){
        final RobotEntity r;
        try{
            r = RobotEntity.create(jsonObj);
        }catch(JSONException e){
            throw new BadRequestException();
        }
        return (RobotEntity) DatabaseUtil.runRequest(new DatabaseRequest() {
          
            public Object request(Session session) {
                User u = (User) session.get(User.class, userId);
                r.setUser(u);
                session.saveOrUpdate(r);
                return r;
            }
        });
    }

    public boolean deleteRobot(final int userId, final int robotId){
        RobotEntity re = getRobot(userId, robotId);
        return deleteRobot(re);
    }
    /*public boolean deleteRobot(final String username, final int robotId){
        return false;
    }*/
    private boolean deleteRobot(final RobotEntity rb){
        boolean result = (boolean) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                session.delete(rb);
                return true;
            }
        });
        return result;
    }
}
