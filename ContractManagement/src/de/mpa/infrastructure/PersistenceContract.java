package de.mpa.infrastructure;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.mpa.application.LocationService;
import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractTemplate;
import de.mpa.domain.PlaceOfPerformance;
import de.mpa.domain.ConditionOffer;
import de.mpa.domain.Requirement;
import de.mpa.domain.Task;
import de.mpa.domain.Term;
import de.mpa.domain.UserMatch;

@Stateless
@LocalBean
public class PersistenceContract {

	@EJB
	LocationService ls;

	final String DB_URL = "jdbc:mysql://localhost:3306/mpa_contractmanagement";
	final String USER = "root";
	final String PASS = "";
	
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

	public int getPrincipalId(int contractId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Query q = entitymanager.createNamedQuery("get principalId from contract");
		q.setParameter("contractId", contractId);
		int principalId = ((Number) q.getSingleResult()).intValue();
		entitymanager.close();
		emfactory.close();
		
		return principalId;
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

	public ConditionOffer addOfferToCandidateContract(CandidateId candidateId, ConditionOffer new_offer) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Candidate can = entitymanager.find(Candidate.class, candidateId);

		List<ConditionOffer> list = (List<ConditionOffer>) can.getNegotiatedConditions();
		list.add(new_offer);
		can.setNegotiatedConditions(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return new_offer;
	}

	public List<BasicCondition> getAllBasicConditions() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Query q = entitymanager.createNamedQuery("get all conditions");
		@SuppressWarnings("unchecked")
		List<BasicCondition> list = (List<BasicCondition>) q.getResultList();

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return list;
	}

	public List<Contract> getUserContractRelationship(int clientId, CandidateId candidateId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Query q = entitymanager.createNamedQuery("user contract relationship");
		q.setParameter("userId", clientId);
		q.setParameter("userCandidateId", candidateId);
		@SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>) q.getResultList();

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return list;
	}

	public List<ContractTemplate> getAllContractTemplates(int httpRequesterId){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Query q = entitymanager.createNamedQuery("get principal contract templates");
		q.setParameter("userId", httpRequesterId);
		@SuppressWarnings("unchecked")
		List<ContractTemplate> list = (List<ContractTemplate>) q.getResultList();

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return list;
	}
	
	public Term persistTermInContractTemplate(ContractTemplate c, Term t) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ContractManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		c = entitymanager.merge(c);

		List<Term> list = (List<Term>) c.getTerms();
		list.add(t);
		c.setTerms(list);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return t;
	}
	
	public static void main(String[] args) {
		PersistenceContract pc = new PersistenceContract();

		List<UserMatch> matches = pc.getContractUserMatches();

		Map<Integer, List<UserMatch>> groupedMatches = matches.stream()
				.collect(Collectors.groupingBy(UserMatch::getPrincipalId));

		List<List<UserMatch>> principalMatches = new ArrayList<List<UserMatch>>(groupedMatches.values());
		
		for(List<UserMatch> m : principalMatches) {
			for(UserMatch um : m) {
				System.out.println("PrincipalID: " + um.getPrincipalId());
				System.out.println("UserID: " + um.getUserId());
			}
			System.out.println("Next principal");
		}

		

	}

	// DB connection with jdbc ==> reason: JPA is entity bounded
	public List<UserMatch> getContractUserMatches() {

		Connection conn = null;
		Statement stmt = null;

		List<UserMatch> result = new ArrayList<UserMatch>();

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT (b.ESTIMATEDWORKLOAD-c.MAXWORKLOAD) workloadDif, (b.FEE - c.MINFEE) feeDif, "
					+ "b.TELEWORKPOSSIBLE, con.contractId contractId, con.NAME name, con.principalId principalId, "
					+ "g.LATITUDE gLatitude, g.LONGITUDE gLongitude, g.RADIUS radius, u.userId userId, p.LATITUDE pLatitude, p.LONGITUDE pLongitude, "
					// Start jaro winkler similarit<
					+ "(SELECT AVG(jaro_winkler_similarity(r.DESCRIPTION, q.DESCRIPTION)) "
					+ "FROM mpa_contractmanagement.requirement r, mpa_contractmanagement.contract_requirement cr,"
					+ "mpa_identitymanagement.qualification q, mpa_identitymanagement.user_qualification uq "
					+ "WHERE r.REQUIREMENTID = cr.requirementsProfile_REQUIREMENTID AND "
					+ "q.QUALIFICATIONID = uq.qualificationProfile_QUALIFICATIONID AND " 
					+ "uq.User_USERID = u.USERID AND cr.Contract_CONTRACTID = con.CONTRACTID) jaro "
					// End jaro winkler similarity
					+ "FROM mpa_contractmanagement.contract con, mpa_contractmanagement.basiccondition b, mpa_contractmanagement.placeofperformance p, "
					+ "mpa_identitymanagement.conditiondesire c, "
					+ "mpa_identitymanagement.geographicalcondition g, mpa_identitymanagement.user u "
					+ "WHERE c.CONTRACTTYPE = con.TYPE "
					+ "AND con.BASICCONDITIONS_BASICCONDITIONID = b.BASICCONDITIONID "
					+ "AND p.PLACEID = con.PLACEOFPERFORMANCE_PLACEID "
					+ "AND g.PLACEID = c.PLACE_PLACEID "
					+ "AND u.CD_DESIREID = c.desireId "
					+ "AND NOT con.principalId = u.userId "
					+ "AND b.ESTIMATEDWORKLOAD <= c.MAXWORKLOAD*1.3 " 
					+ "AND b.FEE >= c.MINFEE*0.7 "
					+ "AND b.ENDDATE <= c.EARLIESTENDDATE " 
					+ "AND b.STARTDATE >= c.EARLIESTSTARTDATE "
					+ "ORDER BY con.principalId, u.userId, jaro DESC, (b.FEE - c.MINFEE), (b.ESTIMATEDWORKLOAD - c.MAXWORKLOAD)";

			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			
			// STEP 5: Extract data from result set
			while (rs.next()) {
				
				// Retrieve by column name
				int princiaplId = rs.getInt("principalId");
				int userId = rs.getInt("userId");
				int contractId = rs.getInt("contractId");
				String contractSubject = rs.getString("name");
				double lat1 = rs.getDouble("gLatitude");
				double lng1 = rs.getDouble("gLongitude");
				double lat2 = rs.getDouble("pLatitude");
				double lng2 = rs.getDouble("pLongitude");
				int radius = rs.getInt("radius");
				boolean teleWorkPossible = rs.getBoolean("teleWorkPossible");

				double distance = this.getDistance(lat1, lng1, lat2, lng2);

				if (!teleWorkPossible) {
					if (distance <= radius && userId != princiaplId) {
						UserMatch um = new UserMatch(princiaplId, contractId, contractSubject, userId);
						result.add(um);
					}
				} else {
					UserMatch um = new UserMatch(princiaplId, contractId, contractSubject, userId);
					result.add(um);
				}

			}

			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return result;
	}

	private double getDistance(double lat1, double lng1, double lat2, double lng2) {

		double earthRadius = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

}
