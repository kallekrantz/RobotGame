package com.robotgame.storage.database;

import org.hibernate.Session;

/**
 * This is a class encapsulting a DatabaseRequest.
 * Send it to DatabaseUtil.runRequest.
 */
public interface DatabaseRequest {
    Object request(Session session);
}
