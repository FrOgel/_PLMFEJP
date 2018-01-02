package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ContactPerson{
	
	//Attribute declaration
	@Id
	private String mailAddress;
	private String firstName, surName, phoneNumber, department;
	//----------------------
	
	//Constructor
	public ContactPerson() {
		
	}
	public ContactPerson(String firstName, String surName, String phoneNumber, String mailAddress, String department) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.phoneNumber = phoneNumber;
		this.mailAddress = mailAddress;
		this.department = department;
	}
	//------------
	
	//Setter and getter
	@XmlElement
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@XmlElement
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	@XmlElement
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@XmlElement
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	@XmlElement
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	//----------------------
	
	
}