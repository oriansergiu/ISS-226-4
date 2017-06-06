package repository.impl;

import model.ConferenceSession;
import model.Paper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.ConferenceSessionRepository;
import util.HibernateUtil;

import java.util.List;

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

    @Override
    public void save(ConferenceSession conferenceSession) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(conferenceSession);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(ConferenceSession conferenceSession) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(conferenceSession);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<ConferenceSession> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ConferenceSession> list = session.createQuery("from ConferenceSession").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public void delete(ConferenceSession conferenceSession) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(conferenceSession);
        session.getTransaction().commit();
        session.close();
    }

}
