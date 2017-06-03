package repository.impl;

import model.Reviewer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.ReviewerRepository;
import util.HibernateUtil;

public class DefaultReviewerRepository implements ReviewerRepository {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Reviewer reviewer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(reviewer);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Reviewer reviewer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(reviewer);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Reviewer getReviewerByUserId(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Reviewer reviewer = (Reviewer) session.createQuery("from Reviewer r where r.user.id=:id")
                .setParameter("id", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return reviewer;
    }
}
