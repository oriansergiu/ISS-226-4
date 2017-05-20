package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    private static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            initialize();
        return sessionFactory;
    }

}
