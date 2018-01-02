package de.mpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="userID")
@XmlRootElement
public class CompanyUser extends User{
	
	//Attribute declaration
	private String companyName;
	@OneToMany(cascade = CascadeType.ALL)
	private List<ContactPerson> contactPersons = new ArrayList<ContactPerson>();
	@OneToOne(cascade = CascadeType.ALL)
	private ContactPerson mainContactPerson;
	//------------------------
	
	//Constructors
	public CompanyUser() {
		
	}
	public CompanyUser(int userID, String mail, String pw, String phoneNumber, String companyName, Address address, ContactPerson mCP){
		super(userID, mail, pw, phoneNumber, address);
		this.companyName = companyName;
		this.mainContactPerson = mCP;
	}
	public CompanyUser(String mail, String pw, String phoneNumber, String companyName, Address address, ContactPerson mCP){
		super(mail, pw, phoneNumber, address);
		this.companyName = companyName;
		this.mainContactPerson = mCP;
	}
	//------------------------
	
	//Manage contact persons
	public void addContactPerson(ContactPerson cp) {
		contactPersons.add(cp);
	}
	public void removeContactPersonByMail(String mail) {
		for(ContactPerson cp : contactPersons) {
			if(cp.getMailAddress().equals(mail)){
				contactPersons.remove(cp);
			}
		}
	}
	//----------------------
	
	//Setters and getters
	@XmlElement
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@XmlElement
	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}
	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}
	@XmlElement
	public ContactPerson getMainContactPerson() {
		return mainContactPerson;
	}
	public void setMainContactPerson(ContactPerson mainContactPerson) {
		this.mainContactPerson = mainContactPerson;
	}
	//-------------------------
	
	
}

