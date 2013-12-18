package com.robotgame.storage.database;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Creates Databasesessions for different threads.
 */

public class SessionCreator {
    private static SessionFactory sessionFactory;

    static
    {
        try
        {
            Configuration config = new Configuration().configure();
            ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(config
                    .getProperties());
            sessionFactory = config
                    .buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        }
        catch (HibernateException he)
        {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }
    public static SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;
    }

}
