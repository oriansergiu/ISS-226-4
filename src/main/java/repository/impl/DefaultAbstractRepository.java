package repository.impl;

import model.Abstract;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.AbstractRepository;
import util.HibernateUtil;

import java.util.List;

public class DefaultAbstractRepository implements AbstractRepository {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Abstract _abstract) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(_abstract);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Abstract _abstract) {
        //The abstract that gets here has to be a persisted one
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(_abstract);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Abstract _abstract) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(_abstract);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Abstract getAbstractById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Abstract _abstract = session.load(Abstract.class, id);
        session.getTransaction().commit();
        session.close();
        return _abstract;
    }

    @Override
    public List<Abstract> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Abstract> list = session.createQuery("from Abstract").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }
}
