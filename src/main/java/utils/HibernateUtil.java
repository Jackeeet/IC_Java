//package utils;
//
////import dbo.Message;
//import dbo.User;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//    private static SessionFactory sessionFactory;
//
//    private HibernateUtil() {
//    }
//
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration().configure();
////                configuration.addAnnotatedClass(User.class);
////                configuration.addAnnotatedClass(Message.class);
//                StandardServiceRegistryBuilder builder =
//                        new StandardServiceRegistryBuilder()
//                                .applySettings(configuration.getProperties());
//                configuration.buildSessionFactory(builder.build());
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return sessionFactory;
//    }
//}
//
//
package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}