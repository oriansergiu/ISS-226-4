package repository.impl;

import model.ConferenceSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.ConferenceSessionRepository;
import util.HibernateUtil;

public class DefaultConferenceSessionRepository implements ConferenceSessionRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public ConferenceSession findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ConferenceSession conferenceSession = session.load(ConferenceSession.class, id);
        session.getTransaction().commit();
        session.close();

        return conferenceSession;
    }
}
