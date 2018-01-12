package de.mpa.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Child from SecurityValidation to verify the account
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="userID")
public class AccountVerification extends SecurityValidation{
	
	public AccountVerification() {
		super();
	}
	public AccountVerification(int userID) {
		super(userID);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 12);
		this.setExpirationDate(String.valueOf(cal.getTimeInMillis()));
	}
	
}
