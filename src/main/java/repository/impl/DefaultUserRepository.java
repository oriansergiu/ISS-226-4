package repository.impl;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.UserRepository;
import util.HibernateUtil;

import java.util.List;

public class DefaultUserRepository implements UserRepository {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public void update(User user) {
        //The User that gets here has to be a persisted one
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.load(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public User getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.createQuery("from User p where p.email = :email")
                .setParameter("email", email).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
