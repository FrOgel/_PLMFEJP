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
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Superclass for the
 *         general user attributes and methods
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(query = "SELECT u FROM User u WHERE u.mailAddress = :mail AND u.password = :password", name = "find user by pw and mail"),
	@NamedQuery(query = "SELECT u.userID FROM User u WHERE u.mailAddress = :mail", name = "get userId by mail"),
	@NamedQuery(query = "SELECT u.mailAddress FROM User u WHERE u.userID = :userId", name = "get mail by userId"),
})	

@XmlRootElement
public class User {
	
	public static class InternalView extends OwnerView{}
	public static class OwnerView extends PartnerView{}
	public static class PartnerView extends ViewerView{}
	public static class ViewerView{};

	
	// Attribute declaration
	@JsonView(User.ViewerView.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;
	@Column(unique = true)
	@JsonView(User.PartnerView.class)
	private String mailAddress;
	@JsonView(User.InternalView.class)
	private String password;
	@JsonView(User.PartnerView.class)
	private String phoneNumber;
	@JsonView(User.ViewerView.class)
	@OneToMany(cascade = CascadeType.ALL)
	private List<Qualification> qualificationProfile;
	@JsonView(User.OwnerView.class)
	@OneToOne(cascade = CascadeType.ALL)
	private Address userAddress;
	@JsonView(User.ViewerView.class)
	@OneToOne(cascade = CascadeType.ALL)
	private ConditionDesire cd;
	@JsonView(User.InternalView.class)
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

	
	public boolean isConditionNull() {
		if(cd==null)
			return true;
					
		return false;
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

	@XmlElement
	public ConditionDesire getCd() {
		return cd;
	}

	public void setCd(ConditionDesire cd) {
		this.cd = cd;
	}

	
}