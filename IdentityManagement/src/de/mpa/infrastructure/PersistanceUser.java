package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import de.mpa.domain.AccountVerification;
import de.mpa.domain.PasswordChange;
import de.mpa.domain.Qualification;
import de.mpa.domain.SecurityValidation;
import de.mpa.domain.User;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Class for persisting,
 *         deleting, updating and retrieving user related details
 */
@Stateless
public class PersistanceUser {
	public Object addObjectToPersistance(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(o);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return o;
	}

	public Object getObjectFromPersistanceById(Class<?> c, int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		Object o = entitymanager.find(c, id);
		entitymanager.close();
		emfactory.close();
		return o;
	}

	public User persistVerifiedUser(User user, AccountVerification av) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		entitymanager.merge(user).setVerified(true);
		entitymanager.remove(entitymanager.merge(av));
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return user;
	}

	public User checkUserCredentials(String mail, String password) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();

		Query q = entitymanager.createNamedQuery("find user by pw and mail");
		q.setParameter("mail", mail);
		q.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) q.getResultList();

		entitymanager.close();
		emfactory.close();

		if ((list != null) && (list.size() > 0)) {
			for (User u : list) {
				return u;
			}
			;
			return null;
		} else {
			return null;
		}
	}
	
	public int findUserIdByMail(String mail) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();

		Query q = entitymanager.createNamedQuery("get userId by mail");
		q.setParameter("mail", mail);
		@SuppressWarnings("unchecked")
		List<Integer> id = (List<Integer>) q.getResultList();
		
		entitymanager.close();
		emfactory.close();

		if ((id != null) && (id.size() > 0)) {
			for (Integer i : id) {
				return i;
			}
			;
			return 0;
		} else {
			return 0;
		}
	}
	
	public boolean checkIfValidationExists(int userId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();

		Query q = entitymanager.createNamedQuery("check if validation exists");
		q.setParameter("userID", userId);
		
		long count = (long) q.getSingleResult();
		
		if(count == 0) {
			return false;
		}else {
			return true;
		}
		
	}
	
	public PasswordChange findPasswordChange(String uuid) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();

		Query q = entitymanager.createNamedQuery("find pChange by uuid");
		q.setParameter("uuid", uuid);
		@SuppressWarnings("unchecked")
		List<PasswordChange> list = (List<PasswordChange>) q.getResultList();
		
		entitymanager.close();
		emfactory.close();

		if ((list != null) && (list.size() > 0)) {
			for (PasswordChange pc : list) {
				return pc;
			}
			;
			return null;
		} else {
			return null;
		}
	}

	public Qualification updateQualification(Qualification q_old, Qualification q_new) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		q_old = entitymanager.merge(q_old);

		if (!(q_new.getDescription().equals("")) && (!(q_old.getDescription().equals(q_new.getDescription())))) {
			q_old.setDescription(q_new.getDescription());
		}

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return q_old;
	}
	
	public void removceSecurityValidation(int id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		SecurityValidation sc = entitymanager.find(PasswordChange.class, id);
		entitymanager.remove(sc);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}

	public boolean changePassword(User u, String newPassword) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("IdentityManagement");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		entitymanager.merge(u).setPassword(newPassword);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return true;
	}
}
