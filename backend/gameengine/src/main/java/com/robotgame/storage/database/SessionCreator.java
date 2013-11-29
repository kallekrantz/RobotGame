package com.robotgame.storage.database;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Creates Databasesessions for different threads.
 */

public class SessionCreator {
    private static Configuration config = null;
    public static void setConfig(Configuration config){
        SessionCreator.config = config.configure();
    }
    public static SessionFactory getSessionFactory() throws HibernateException {
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(config
                .getProperties());
        SessionFactory sessionFactory = config
                .buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        return sessionFactory;
    }
}
