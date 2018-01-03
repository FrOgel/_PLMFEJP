package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	@XmlElement
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@XmlElement
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	//---------------------
	
}
