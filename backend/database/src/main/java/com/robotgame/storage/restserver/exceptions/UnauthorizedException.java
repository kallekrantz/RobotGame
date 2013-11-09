package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 11/6/13
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnauthorizedException extends WebApplicationException{
    public UnauthorizedException(){
        super(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
