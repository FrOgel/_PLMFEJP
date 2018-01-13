package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.PlaceOfPerformance;
import de.mpa.domain.ConditionOffer;
import de.mpa.domain.Requirement;
import de.mpa.domain.Term;
import de.mpa.domain.Task;

@Stateless
public class PersistanceContract {

	public Object addObjectToPersistance(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(o);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return o;
	}

	public Object updateExistingObject(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Object attached = entitymanager.merge(o);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return attached;
	}

	public Object getObjectFromPersistanceById(Class<?> c, Object id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Object o = entitymanager.find(c, id);
		entitymanager.close();
		emfactory.close();
		return o;
	}

	public boolean deleteObjectFromPersistance(Class<?> c, Object id) {
		try {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
			EntityManager entitymanager = emfactory.createEntityManager();
			entitymanager.getTransaction().begin();
			Object o = entitymanager.find(c, id);
			entitymanager.remove(o);
			entitymanager.getTransaction().commit();
			entitymanager.close();
			emfactory.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkIfContractIdMatchesRequester(int contractId, int requesterId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Query q = entitymanager.createNamedQuery("check requesterId");
		q.setParameter("contractId", requesterId);
		q.setParameter("requesterId", requesterId);
		int count = ((Number) q.getSingleResult()).intValue();
		System.out.println(count);
		entitymanager.close();
		emfactory.close();

		if (count != 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Contract> searchContract(String searchString) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Query q = entitymanager.createNamedQuery("search contracts");
		q.setParameter("searchString", "%" + searchString + "%");
		@SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>) q.getResultList();
		
		entitymanager.close();
		emfactory.close();

		return list;
	}
	
	public PlaceOfPerformance persistPoPInContract(PlaceOfPerformance p_new, int contractId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Contract c = entitymanager.find(Contract.class, contractId);
		
		c.setPlaceOfPerformance(p_new);
	
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return p_new;
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

	public BasicCondition persistBasicConditionInContract(Contract c, BasicCondition b) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		c.setBasicConditions(b);

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

	public Term persistTermInContract(Contract c, Term s) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Term> list = (List<Term>) c.getContractTerms();
		list.add(s);
		c.setContractTerms(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return s;
	}

	public Candidate persistCandidateInContract(Contract c, Candidate can) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Candidate> list = (List<Candidate>) c.getCandidates();
		list.add(can);
		c.setCandidates(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return can;
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

	public boolean deleteTermFromContract(int contractId, int conditionId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Contract c = entitymanager.find(Contract.class, contractId);
		Term s = entitymanager.find(Term.class, conditionId);
		List<Term> list = (List<Term>) c.getContractTerms();
		list.remove(s);
		entitymanager.remove(s);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public boolean declineCandidateInContract(CandidateId candidateId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Candidate can = entitymanager.find(Candidate.class, candidateId);
		can.setCandidateAccepted(false);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}

	public boolean doesCandiateAlreadyExistInContract(CandidateId candidateId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Query q = entitymanager.createNamedQuery("does candidate exist");
		q.setParameter("candidateId", candidateId);
		int count = ((Number) q.getSingleResult()).intValue();
		System.out.println(count);
		entitymanager.close();
		emfactory.close();

		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addCandidateToContract(Contract c, Candidate candidate) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Candidate> list = (List<Candidate>) c.getCandidates();
		list.add(candidate);
		c.setCandidates(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return true;
	}

	public ConditionOffer addOfferToCandidateContract(CandidateId candidateId, BasicCondition bc, ConditionOffer nc) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Candidate can = entitymanager.find(Candidate.class, candidateId);

		List<ConditionOffer> list = (List<ConditionOffer>) can.getNegotiatedConditions();
		list.add(nc);
		nc.setCondition(bc);
		can.setNegotiatedConditions(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return nc;
	}

}
