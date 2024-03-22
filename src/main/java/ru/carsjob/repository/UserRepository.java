package ru.carsjob.repository;

import ru.carsjob.model.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
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
    @Transactional
    public User create(User user) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    @Transactional
    public void update(User user) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    @Transactional
    public void delete(Long userId) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = new User();
        user.setId(userId);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    @Transactional
    public List<User> findAllOrderById() {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        List<User> users  = session.createQuery("from User order by id", User.class).list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    @Transactional
    public Optional<User> findById(Long userId) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, userId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    @Transactional
    public List<User> findByLikeLogin(String key) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        List<User> users  = session.createQuery("from User where login like :key", User.class)
                .setParameter("key", "%" + key + "%")
                .list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    @Transactional
    public Optional<User> findByLogin(String login) {
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = session.createQuery("from User where login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(user);
    }
}
