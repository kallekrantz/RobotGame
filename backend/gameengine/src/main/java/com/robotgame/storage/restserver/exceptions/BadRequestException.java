package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
    public BadRequestException(){
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }
}
