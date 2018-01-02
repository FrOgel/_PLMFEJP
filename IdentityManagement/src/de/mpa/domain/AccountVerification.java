package de.mpa.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

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
