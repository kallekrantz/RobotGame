package com.robotgame.storage.entities;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 20/11/13
 * Time: 08:46
 * To change this template use File | Settings | File Templates.
 */
public interface EntityInterface {
    public EntityInterface merge(JSONObject obj) throws JSONException;
}
