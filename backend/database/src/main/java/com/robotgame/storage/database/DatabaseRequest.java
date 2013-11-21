package com.robotgame.storage.database;

import org.hibernate.Session;

public interface DatabaseRequest {
    Object request(Session session);
}
