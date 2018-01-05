package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
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
	
	public Task persistTaskInContract(Contract c, Task t) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
	    c = entitymanager.merge(c);
	    
		List<Task> list = (List<Task>)c.getTaskDescription();
		list.add(t);
		c.setTaskDescription(list);
		
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return t;
	}
	
	public BasicCondition persistBasicCondition(Contract c, BasicCondition b) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
	    c = entitymanager.merge(c);
	    
	    c.setBasicConditions(b);
	    
	    //b = entitymanager.merge(b);
	    
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return b;
	}
	
	public Requirement persistRequirementInContract(Contract c, Requirement r) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
	    c = entitymanager.merge(c);
	    
		List<Requirement> list = (List<Requirement>)c.getRequirementsProfile();
		list.add(r);
		c.setRequirementsProfile(list);
	
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return r;
	}
	
	public SpecialCondition persistSpecialConditionInContract(Contract c, SpecialCondition s) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
	    c = entitymanager.merge(c);
	    
		List<SpecialCondition> list = (List<SpecialCondition>)c.getSpecialConditions();
		list.add(s);
		c.setSpecialConditions(list);
		
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return s;
	}

	public List<Contract> findUserContracts(int principalId){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ContractManagement" );  
	    EntityManager entitymanager = emfactory.createEntityManager( );
	   
	    Query q = entitymanager.createNamedQuery("find user contracts");
	    q.setParameter("principalID", principalId);
	    @SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>) q.getResultList();
	    
	    entitymanager.close();
	    emfactory.close();
	    
	    return list;
	}
}
