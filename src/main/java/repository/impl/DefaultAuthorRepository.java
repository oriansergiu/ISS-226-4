package repository.impl;

import model.Author;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.AuthorRepository;
import util.HibernateUtil;

import java.util.List;

public class DefaultAuthorRepository implements AuthorRepository {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Author author) {
        //The author that gets here has to be a persisted one
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(author);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Author getAuthorById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author = session.load(Author.class, id);
        session.getTransaction().commit();
        session.close();
        return author;
    }

    @Override
    public Author getAuthorByUserId(User id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author = (Author) session.createQuery("from Author a where a.user = :user").setParameter("user", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return author;
    }

    @Override
    public List<Author> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Author> list = session.createQuery("from Author").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }
}
