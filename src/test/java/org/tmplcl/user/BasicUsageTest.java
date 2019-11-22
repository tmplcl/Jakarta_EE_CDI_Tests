package org.tmplcl.user;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import testutil.EntityManagerFactoryProducer;
import testutil.EntityManagerProducer;
import testutil.JtaEnvironment;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.persistence.EntityManager;
import java.util.List;

@ExtendWith({WeldJunit5Extension.class, JtaEnvironment.class})
class BasicUsageTest {

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
            .from(EntityManagerFactoryProducer.class, EntityManagerProducer.class, EntityManager.class, BeanManager.class)
            .activate(RequestScoped.class, ApplicationScoped.class).build();

    @Test
    public void testFoo(EntityManager entityManager) {
        Assertions.assertNotNull(entityManager);
        entityManager.getTransaction().begin();
        User u1 = new User();
        u1.setFirstName("fritz");
        u1.setLastName("walter");

        entityManager.persist(u1);
        entityManager.getTransaction().commit();

        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();
        users.forEach(System.out::println);
        Assertions.assertFalse(users.isEmpty());

    }
}
