package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 10/12/13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class InternalServerErrorException extends WebApplicationException {
    public InternalServerErrorException(){
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}