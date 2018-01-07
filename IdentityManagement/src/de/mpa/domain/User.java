package de.mpa.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Superclass for the
 *         general user attributes and methods
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(query = "SELECT u FROM User u WHERE u.mailAddress = :mail AND u.password = :password", name = "find user by pw and mail"),
	@NamedQuery(query = "SELECT u.userID FROM User u WHERE u.mailAddress = :mail", name = "get userId by mail"),
})	

@XmlRootElement
public class User {

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;
	@Column(unique = true)
	private String mailAddress;
	private String password;
	private String phoneNumber;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Qualification> qualificationProfile;
	@OneToOne(cascade = CascadeType.ALL)
	private Address userAddress;
	private boolean verified;
	// -------------------------

	// Constructor to register company users
	public User() {

	}

	public User(int userID, String mail, String pw, String phoneNumber, Address address) {
		this.userID = userID;
		this.mailAddress = mail;
		this.userAddress = address;
		this.password = pw;
		this.phoneNumber = phoneNumber;
	}

	public User(String mail, String pw, String phoneNumber, Address address) {
		this.mailAddress = mail;
		this.userAddress = address;
		this.password = pw;
		this.phoneNumber = phoneNumber;
	}

	// Setter and getter
	@XmlElement
	public boolean getVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@XmlElement
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@XmlElement
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@XmlElement
	@JsonIgnore
	public Address getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(Address userAddress) {
		this.userAddress = userAddress;
	}

	public List<Qualification> getQualificationProfile() {
		return qualificationProfile;
	}

	public void setQualificationProfile(List<Qualification> qualificationProfile) {
		this.qualificationProfile = qualificationProfile;
	}

}