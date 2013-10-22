package com.robotgame.database;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Hello world!
 *
*/
@Path("myresource")
public class MyResource{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(){
        return "Wat";
    }
}
