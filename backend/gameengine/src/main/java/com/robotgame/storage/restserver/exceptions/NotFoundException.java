package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {
    public NotFoundException(){
        super(Response.status(Response.Status.NOT_FOUND).build());
    }
}
