package org.tmplcl.user;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.jnp.server.NamingBeanImpl;
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
import javax.transaction.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({WeldJunit5Extension.class, JtaEnvironment.class})
public class UserDaoTest {

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
            .from(UserDao.class, EntityManagerFactoryProducer.class, EntityManagerProducer.class, EntityManager.class, BeanManager.class)
            .activate(RequestScoped.class, ApplicationScoped.class)
            .inject(this)
            .build();

    private NamingBeanImpl NAMING_BEAN;


    @Test
    public void testEntityManagerInjection(EntityManager em) {
        assertNotNull(em);
    }

    @Test
    public void testFindAll(EntityManager em, UserDao dao, UserTransaction ut) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

//        ut.begin();
        User u = new User();
        u.setFirstName("first");
        u.setLastName("last");
        dao.create(u);
//        ut.commit();


        assertFalse(dao.findAll().isEmpty());

    }

    @Transactional
    @Test
    public void canUseDeclarativeTxControl(UserDao dao, UserTransaction ut) throws Exception {
        try {
            dao.doSomething();
//            fail("Exception raised due to missing yet required transaction wasn't raised");
        }
        catch(TransactionalException e) {
            assertTrue(e.getMessage().contains("ARJUNA016110"));
        }

        ut.begin();
        assertEquals("Success", dao.doSomething());
        ut.rollback();
    }

}
