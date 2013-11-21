package com.robotgame.storage.database;


import org.hibernate.Session;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;

public class DatabaseUtil {
    public static Object runRequest(final DatabaseRequest strategy) {
        Session session = SessionCreator.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            Object object = strategy.request(session);
            session.getTransaction().commit();
            return object;
        } catch (WebApplicationException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            throw e;
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
            throw new InternalServerErrorException();
        } finally {
            session.disconnect();
        }
    }
}
