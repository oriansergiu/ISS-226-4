package repository.impl;


import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.ListenerRepository;
import util.HibernateUtil;

public class DefaultListenerRepository implements ListenerRepository {
//    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//    @Override
//    public void addListenerToSection(User user,Integer sectionID) {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.createQuery(" NSERT INTO sections_participants values (:sectionID,:userID)")
//                .setParameter("userID", user.getId()).uniqueResult();
//
//        session.getTransaction().commit();
//        session.close();
//    }
}
