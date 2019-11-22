package org.tmplcl.user;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@ApplicationScoped
public class PersistenceContextCreator {

    @PersistenceContext
    EntityManager em;

//     @Produces
     public EntityManager produce(){
         return this.em;
     }

    
}
