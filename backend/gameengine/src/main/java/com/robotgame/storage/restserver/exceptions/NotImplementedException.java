package com.robotgame.storage.restserver.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by kallekrantz on 19/12/13.
 */
public class NotImplementedException extends WebApplicationException {
    public NotImplementedException(){
        super(Response.status(Response.Status.NOT_IMPLEMENTED).build());
    }
}

