package de.mpa.domain;


//This class is caching user objects
public class UserRepository {

	
	//Static attributes to have consistency over the multiple class instances
	private static int cacheCounter = 0;
	private static User userCache[] = new User[49];
	//----------------------------------------------------
	
	
	//Authenticating the user based on the mail address and the password
	public String authenticate(String mail, String pw) {
		return "1";
	}
	
	//Method to add the user to the repository cache
	public void addUserToCache(User user) {
		userCache[cacheCounter] = user;
		if(cacheCounter == 49) {
			cacheCounter = 0;
		}else {
			cacheCounter++;
		}
	}		
	//-------------------------------------------------------------------------
	
}
