package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 10/12/13
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class BadRequestException extends WebApplicationException {
    public BadRequestException(){
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }
}
