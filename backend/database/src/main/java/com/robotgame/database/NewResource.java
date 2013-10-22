package com.robotgame.database;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/22/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("myresource/restful")
public class NewResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getRest(){
        return "wat!";
    }
}
