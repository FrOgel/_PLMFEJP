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
import de.mpa.domain.ContractState;
import de.mpa.domain.ConditionOffer;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;
import de.mpa.domain.TaskType;

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

	public Object getObjectFromPersistanceById(Class<?> c, Object id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Object o = entitymanager.find(c, id);
		entitymanager.close();
		emfactory.close();
		return o;
	}

	private boolean deleteObjectFromPersistance(Class<?> c, Object id) {
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

	public boolean declineCandidateInContract(CandidateId candidateId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		Candidate can = entitymanager.find(Candidate.class, candidateId);
		can.setAccepted(false);
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

	public Contract updateContract(Contract c_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		//Contract attached = entitymanager.find(Contract.class, c_new.getContractID());
		Contract attached = entitymanager.merge(c_new);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return attached;
	}

	public Task updateTask(Task t_old, Task t_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		t_old = entitymanager.merge(t_old);

		if ((t_new.getDescription() != null) && (!(t_old.getDescription().equals(t_new.getDescription())))) {
			t_old.setDescription(t_new.getDescription());
		}

		if ((t_new.getType() != null) && (!(t_old.getType().equals(t_new.getType())))) {
			t_old.setType(t_new.getType());
		}

		if ((t_new.getType() != null) && (t_new.getType().equals(TaskType.FUNCTIONAL))) {
			t_old.setSubType(null);
		} else {
			if ((t_new.getSubType() != null) && (!(t_old.getSubType().equals(t_new.getSubType())))) {
				t_old.setSubType(t_new.getSubType());
			}
		}

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return t_old;
	}

	public BasicCondition updateBasicCondition(BasicCondition b_old, BasicCondition b_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		b_old = entitymanager.merge(b_old);

		if (!(b_new.getLocation().equals("")) && (!(b_old.getLocation().equals(b_new.getLocation())))) {
			b_old.setLocation(b_new.getLocation());
		}
		if ((b_new.getEndDate() != null) && (!(b_old.getEndDate().equals(b_new.getEndDate())))) {
			b_old.setEndDate(b_new.getEndDate());
		}
		if ((b_new.getStartDate() != null) && (!(b_old.getStartDate().equals(b_new.getStartDate())))) {
			b_old.setStartDate(b_new.getStartDate());
		}
		if ((!(b_old.getRadius() == b_new.getRadius()))) {
			b_old.setRadius(b_new.getRadius());
		}
		if ((!(b_old.getEstimatedWorkload() == b_new.getEstimatedWorkload()))) {
			b_old.setEstimatedWorkload(b_new.getEstimatedWorkload());
		}
		if ((b_new.getFee() != 0) && (!(b_old.getFee() == b_new.getFee()))) {
			b_old.setFee(b_new.getFee());
		}

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return b_old;
	}

	public Requirement updateRequirement(Requirement r_old, Requirement r_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		r_old = entitymanager.merge(r_old);

		if (!(r_new.getDescription().equals("")) && (!(r_old.getDescription().equals(r_new.getDescription())))) {
			r_old.setDescription(r_new.getDescription());
		}

		if ((r_new.getCriteriaType() != null) && (!(r_old.getCriteriaType().equals(r_new.getCriteriaType())))) {
			r_old.setCriteriaType(r_new.getCriteriaType());
		}

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return r_old;
	}

	public SpecialCondition updateSpecialCondition(SpecialCondition s_old, SpecialCondition s_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		s_old = entitymanager.merge(s_old);

		if (!(s_new.getDescription().equals("")) && (!(s_old.getDescription().equals(s_new.getDescription())))) {
			s_old.setDescription(s_new.getDescription());
		}

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return s_old;
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

	public boolean acceptCandidateInContract(CandidateId candidateId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Contract c = entitymanager.find(Contract.class, candidateId.getContractId());
		Candidate can = entitymanager.find(Candidate.class, candidateId);
		List<ConditionOffer> list = (List<ConditionOffer>) can.getNegotiatedConditions();
		ConditionOffer nc = new ConditionOffer();
		nc.setCondition(c.getBasicConditions());
		nc.setSenderId(c.getPrincipalID());
		nc.setReceiverId(candidateId.getCandidateId());
		list.add(nc);
		can.setNegotiatedConditions(list);
		can.setAccepted(true);

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
		can.setAccepted(true);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return nc;
	}

}
