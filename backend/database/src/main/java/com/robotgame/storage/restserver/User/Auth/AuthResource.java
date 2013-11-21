package com.robotgame.storage.restserver.User.Auth;

import com.robotgame.storage.database.DatabaseRequest;
import com.robotgame.storage.database.DatabaseUtil;
import com.robotgame.storage.database.PasswordHasher;
import com.robotgame.storage.database.SessionCreator;
import com.robotgame.storage.entities.AuthToken;
import com.robotgame.storage.entities.User;
import com.robotgame.storage.restserver.exceptions.UnauthorizedException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 11/6/13
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */

@Path("/user/{userid}/auth")
public class AuthResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(@PathParam("userid") final int userid, final JSONObject jsonObj){
        if(!jsonObj.has("password")){
            throw new BadRequestException();
        }
        AuthToken token = (AuthToken) DatabaseUtil.runRequest(new DatabaseRequest() {
            @Override
            public Object request(Session session) {
                User user = (User) session.get(User.class, userid);
                if (user == null) {
                    throw new NotFoundException();
                }
                String password = jsonObj.optString("password", "");
                if (user.getPwdHash().equals(PasswordHasher.hash(password))) {
                    AuthToken token = new AuthToken(user);
                    session.save(token);
                    return token;
                } else {
                    throw new UnauthorizedException();
                }
            }
        });
        return Response.ok(token).build();
    }
}
