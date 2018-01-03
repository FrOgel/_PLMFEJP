package de.mpa.infrastructure;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Stateless
public class PersistanceContract {
	
	
	private Object addObjectToPersistance(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	      
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
		
	    entitymanager.persist(o);
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return o;
	}
	private Object getObjectFromPersistanceById(Class<?> c, String id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager();
	    Object o = entitymanager.find( c, Integer.parseInt(id) );
	    entitymanager.close();
	    emfactory.close();
		return o;
	}

}
