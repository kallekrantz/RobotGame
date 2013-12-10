package com.robotgame.storage.database;


import com.robotgame.storage.restserver.exceptions.InternalServerErrorException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.ws.rs.WebApplicationException;

/**
 * Class for running a specific DatabaseRequest.
 * May also contain other things.
 */
public class DatabaseUtil {
    public static Object runRequest(final DatabaseRequest strategy, SessionFactory factory) {
        Session session = factory.openSession();
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
    public static Object runRequest(final DatabaseRequest strategy) {
        return runRequest(strategy, SessionCreator.getSessionFactory());
    }
}
