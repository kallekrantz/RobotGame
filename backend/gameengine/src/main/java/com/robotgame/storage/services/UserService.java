package com.robotgame.storage.services;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.entities.User;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;


/**
 * Userservice for rest endpoint -> database. Currently maps to Hibernate.
 */
public class UserService {
    public User getUser(final int userId){
        return (User) DatabaseUtil.runRequest(new DatabaseRequest() {
           
            public Object request(Session session) {
                return session.get(User.class, userId);
            }
        });
    }

    public User editUser(final int userId, final JSONObject jsonObj){
        User u = (User) DatabaseUtil.runRequest(new DatabaseRequest() {
            
            public Object request(Session session) {
                User u = (User) session.get(User.class, userId);
                if (u == null) {
                    throw new NotFoundException();
                }
                User merged;
                try {
                    merged = (User) session.merge(User.merge(u, jsonObj));
                } catch (JSONException e) {
                    throw new BadRequestException();
                }
                return merged;
            }
        });
        return u;
    }

    public List<User> getAllUsers(){
        List<User> userList = (List<User>) DatabaseUtil.runRequest(new DatabaseRequest() {
            
            public Object request(Session session) {
                return session.createQuery("from com.robotgame.storage.entities.User").list();
            }
        });
        return userList;
    }

    public User createUser(JSONObject jsonObj){
        final User u;
        try {
            u = User.create(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        return (User) DatabaseUtil.runRequest(new DatabaseRequest() {
            
            public Object request(Session session) {
                User merged = (User) session.merge(u);
                session.saveOrUpdate(merged);
                return merged;
            }
        });
    }

}
