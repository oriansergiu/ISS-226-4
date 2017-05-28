package repository.impl;

import model.Paper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.PaperRepository;
import util.HibernateUtil;

import java.util.List;

public class DefaultPaperRepository implements PaperRepository {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Paper paper) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(paper);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Paper paper) {
        //The paper that gets here has to be a persisted one
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(paper);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Paper getPaperById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Paper paper = session.load(Paper.class, id);
        session.getTransaction().commit();
        session.close();
        return paper;
    }

    @Override
    public List<Paper> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Paper> list = session.createQuery("from Paper").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }
}
