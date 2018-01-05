package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.mpa.domain.Contract;
import de.mpa.domain.Task;


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
	private Object getObjectFromPersistanceById(Class<?> c, int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager();
	    Object o = entitymanager.find( c, id);
	    entitymanager.close();
	    emfactory.close();
		return o;
	}

	public Contract persistContract(Contract c) {
		return (Contract) this.addObjectToPersistance(c);
	}
	
	public Contract findContract(int contractId) {
		return (Contract) this.getObjectFromPersistanceById(Contract.class, contractId);
	}
	
	public Task persistTask(Contract c, Task t) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
	    entitymanager.merge(c);
	    
		List<Task> list = (List<Task>)c.getTaskDescription();
		list.add(t);
		c.setTaskDescription(list);
	    entitymanager.persist(t);
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return t;
	}
	
}
