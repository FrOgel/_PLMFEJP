package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Stateless
public class PersistanceContract {

	private Object addObjectToPersistance(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(o);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return o;
	}

	public Object getObjectFromPersistanceById(Class<?> c, int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Object o = entitymanager.find(c, id);
		entitymanager.close();
		emfactory.close();
		return o;
	}

	private boolean deleteObjectFromPersistance(Class<?> c, int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Object o = entitymanager.find(c, id);
		entitymanager.remove(o);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public Contract persistContract(Contract c) {
		return (Contract) this.addObjectToPersistance(c);
	}

	

	public Task persistTaskInContract(Contract c, Task t) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Task> list = (List<Task>) c.getTaskDescription();
		list.add(t);
		c.setTaskDescription(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return t;
	}

	public BasicCondition persistBasicCondition(Contract c, BasicCondition b) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		c.setBasicConditions(b);

		// b = entitymanager.merge(b);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return b;
	}

	public Requirement persistRequirementInContract(Contract c, Requirement r) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Requirement> list = (List<Requirement>) c.getRequirementsProfile();
		list.add(r);
		c.setRequirementsProfile(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return r;
	}

	public SpecialCondition persistSpecialConditionInContract(Contract c, SpecialCondition s) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<SpecialCondition> list = (List<SpecialCondition>) c.getSpecialConditions();
		list.add(s);
		c.setSpecialConditions(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return s;
	}

	public List<Contract> findUserContracts(int principalId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();

		Query q = entitymanager.createNamedQuery("find user contracts");
		q.setParameter("principalID", principalId);
		@SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>) q.getResultList();

		entitymanager.close();
		emfactory.close();

		return list;
	}

	public boolean deleteContract(int contractId) {
		return this.deleteObjectFromPersistance(Contract.class, contractId);
	}

	public boolean deleteTaskFromContracT(int contractId, int taskId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		Task t = entitymanager.find(Task.class, taskId);
		List<Task> list = (List<Task>) c.getTaskDescription();
		list.remove(t);
		entitymanager.remove(t);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public boolean deleteBasicCondition(int contractId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		BasicCondition b = c.getBasicConditions();
		c.setBasicConditions(null);
		entitymanager.remove(b);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return true;
	}

	public boolean deleteRequirementFromContract(int contractId, int requirementId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		Requirement r = entitymanager.find(Requirement.class, requirementId);
		List<Requirement> list = (List<Requirement>) c.getRequirementsProfile();
		list.remove(r);
		entitymanager.remove(r);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public boolean deleteSpecialConditionFromContract(int contractId, int conditionId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		SpecialCondition s = entitymanager.find(SpecialCondition.class, conditionId);
		List<SpecialCondition> list = (List<SpecialCondition>) c.getSpecialConditions();
		list.remove(s);
		entitymanager.remove(s);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public boolean changeContractState(int contractId, ContractState state) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		c.setContractState(state);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public Contract updateContract(Contract c_old, Contract c_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c_old = entitymanager.merge(c_old);

		
		if((c_new.getDesignation()!=null) && (!(c_old.getDesignation().equals(c_new.getDesignation())))) {
			c_old.setDesignation(c_new.getDesignation());
		}
			
		if((c_new.getType()!=null) && (!(c_old.getType().equals(c_new.getType())))) {
			c_old.setType(c_new.getType());
		}
			
		if((c_new.getSubject()!=null) && (!(c_old.getSubject().equals(c_new.getSubject())))) {
			c_old.setSubject(c_new.getSubject());
		}
			
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return c_old;
	}
	
	public Task updateTask(Task t_old, Task t_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		t_old = entitymanager.merge(t_old);
		
		if((t_new.getDescription()!=null) && (!(t_old.getDescription().equals(t_new.getDescription())))) {
			t_old.setDescription(t_new.getDescription());
		}
		
		if((t_new.getType()!=null) && (!(t_old.getType().equals(t_new.getType())))) {
			t_old.setType(t_new.getType());
		}
		
		if((t_new.getSubType()!=null) && (!(t_old.getSubType().equals(t_new.getSubType())))) {
			t_old.setSubType(t_new.getSubType());
		}
		
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return t_old;
	}
	
}
