package repository.impl;

import model.Section;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.SectionRepository;
import util.HibernateUtil;

import java.util.List;

/**
 * Created by Sergiu on 5/22/2017.
 */
public class DefaultSectionRepository implements SectionRepository {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Section findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Section section = session.load(Section.class, id);
        session.getTransaction().commit();
        session.close();

        return section;
    }

    @Override
    public void save(Section section) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(section);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Section section) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(section);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Section> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Section> list = session.createQuery("from Section").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public void delete(Section section) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(section);
        session.getTransaction().commit();
        session.close();
    }
}
