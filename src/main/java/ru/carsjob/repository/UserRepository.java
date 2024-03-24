package ru.carsjob.repository;

import ru.carsjob.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return null;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(Long userId) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            User user = new User();
            user.setId(userId);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            List<User> users  = session
                    .createQuery("from User order by id", User.class)
                    .list();
            session.getTransaction().commit();
            session.close();
            return users;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Collections.emptyList();
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(Long userId) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            Optional<User> user = session
                    .createQuery("from User where id = :id", User.class)
                    .setParameter("id", userId)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            List<User> users  = session.createQuery("from User where login like :key", User.class)
                    .setParameter("key", "%" + key + "%")
                    .list();
            session.getTransaction().commit();
            session.close();
            return users;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Collections.emptyList();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            Optional<User> user = session
                    .createQuery("from User where login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.empty();
    }
}
