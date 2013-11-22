package com.robotgame.storage.database;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 11/3/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
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
