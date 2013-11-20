package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UnauthorizedException extends WebApplicationException{
    public UnauthorizedException(){
        super(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
