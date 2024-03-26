package ru.carsjob.repository;

import lombok.extern.slf4j.Slf4j;
import ru.carsjob.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
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
            log.error(e.getMessage(), e);
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
            session.createQuery("update User set login = :login, password = :password where id = :id")
                   .setParameter("login", user.getLogin())
                   .setParameter("password", user.getPassword())
                   .setParameter("id", user.getId()).executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
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
            session.createQuery("delete from User where id = :id")
                   .setParameter("id", userId)
                   .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
