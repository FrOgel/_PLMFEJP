package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import de.mpa.domain.AccountVerification;
import de.mpa.domain.User;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Class for persisting, deleting, updating and retrieving user related details
 */
@Stateless
public class PersistanceUser {
	private Object addObjectToPersistance(Object o) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "IdentityManagement" );
	      
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
		
	    entitymanager.persist(o);
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	    return o;
	}
	private Object getObjectFromPersistanceById(Class<?> c, String id) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "IdentityManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager();
	    Object o = entitymanager.find( c, Integer.parseInt(id) );
	    entitymanager.close();
	    emfactory.close();
		return o;
	}
	
	public User persistUser(User user) {
		return (User) addObjectToPersistance(user);
	}
	public AccountVerification persistAccountVerification(AccountVerification av) {
		return (AccountVerification) addObjectToPersistance(av);
	}
	
	public AccountVerification getAccountVerification(String id) {
		return (AccountVerification) getObjectFromPersistanceById(AccountVerification.class, id);
	}
	public User getUser(String id) {
		return (User) getObjectFromPersistanceById(User.class, id);
	}

	public void persistVerifiedUser(User user, AccountVerification av) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "IdentityManagement" );
	    EntityManager entitymanager = emfactory.createEntityManager();
	    entitymanager.getTransaction().begin();
	    entitymanager.merge(user).setVerified(true);
	    entitymanager.remove(entitymanager.merge(av));
	    entitymanager.getTransaction().commit();
	    entitymanager.close();
	    emfactory.close();
	}
	public User checkUserCredentials(String mail, String password) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "IdentityManagement" );  
	    EntityManager entitymanager = emfactory.createEntityManager( );
	   
	    Query q = entitymanager.createNamedQuery("find user by pw and mail");
	    q.setParameter("mail", mail);
	    q.setParameter("password", password);
	    @SuppressWarnings("unchecked")
		List<User> list = (List<User>) q.getResultList();
	    
	    entitymanager.close();
	    emfactory.close();
	    
	    if((list!=null) && (list.size()>0)) {
	    	for(User u : list) {
	    		return u;
	    	};
	    	return null;
	    }else {
	    	return null;
	    }
	}
}
