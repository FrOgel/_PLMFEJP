package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="userID")
@XmlRootElement
public class PrivateUser extends User{

	//Attribute declaration
	private String firstName, surName, birthday;
	//-----------------------------

	//Constructor
	public PrivateUser() {
		
	}
	public PrivateUser(int userID, String mail, String pw, String phoneNumber, Address address, 
			String firstName, String surName, String birthday) {
		super(userID, mail, pw, phoneNumber, address);
		this.firstName = firstName;
		this.surName = surName;
		this.birthday = birthday;
	}
	public PrivateUser(String mail, String pw, String phoneNumber, Address address, 
			String firstName, String surName, String birthday) {
		super(mail, pw, phoneNumber, address);
		this.firstName = firstName;
		this.surName = surName;
		this.birthday = birthday;
	}
	//------------
	
	//Setter and getter	
	@XmlElement
	protected String getSurName() {
		return surName;
	}
	protected void setSurName(String surName) {
		this.surName = surName;
	}
	@XmlElement
	protected String getBirthday() {
		return birthday;
	}
	protected void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@XmlElement
	protected String getFirstName() {
		return firstName;
	}
	protected void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//---------------------
	
}
