package de.mpa.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="userID")
public class PasswordChange extends SecurityValidation{

	public PasswordChange() {
		super();
	}
	public PasswordChange(int userID) {
		super(userID);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 4);
		this.setExpirationDate(String.valueOf(cal.getTimeInMillis()));
	}
	
}
