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

    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration().configure();
        ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder().applySettings(configuration
                .getProperties());
        SessionFactory sessionFactory = configuration
                .buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
        return sessionFactory;
    }
}
