package org.tmplcl.user;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.*;

@Dependent
public class UserDao {

    private EntityManager em;

    @Inject
    public UserDao(EntityManager em) {
        this.em = em;
    }

    public User findById(long id) {
        return em.find(User.class, id);
    }

    public User findByBusinessKey(String firstName, String lastName) {
        TypedQuery<User> tq = em.createQuery("select u from User u where u.firstName = :firstName and u.lastName = :lastName", User.class);
        tq.setParameter("firstName", firstName);
        tq.setParameter("lastName", lastName);
        return tq.getSingleResult();
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    public void create(User user) {
        this.em.persist(user);
        this.em.flush();
    }

    @Transactional
    public void update(User user) {
        this.em.merge(user);
    }

    @Transactional
    public void delete(User user) {
        this.em.remove(user);
    }

    @Transactional
    public String doSomething() {
        return "Success";
    }
}
