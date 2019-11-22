package org.tmplcl.user;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * DummyDataGenerator
 */
@ApplicationScoped
public class DummyDataGenerator {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void postConstruct(@Observes @Initialized(ApplicationScoped.class) Object init) {
        User user = new User();
        user.setFirstName("Hans");
        user.setLastName("Peter");

        em.persist(user);
    }

}