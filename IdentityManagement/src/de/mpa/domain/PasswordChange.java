package de.mpa.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Child from SecurityValidation for resetting a users password
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="userID")
@NamedQuery(query = "SELECT p FROM PasswordChange p WHERE p.checkSum = :uuid", name = "find pChange by uuid")
public class PasswordChange extends SecurityValidation{

	public PasswordChange() {
		super();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 4);
		this.setExpirationDate(String.valueOf(cal.getTimeInMillis()));
	}
	public PasswordChange(int userID) {
		super(userID);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 4);
		this.setExpirationDate(String.valueOf(cal.getTimeInMillis()));
	}
	
}
