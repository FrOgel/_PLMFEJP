package de.mpa.domain;

import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="VALIDATION_TYPE")
public class SecurityValidation {
		
	//Attribute decleration
		@Id
		private int userID;
		private String checkSum;
		private String expirationDate;
		
		
		//Constructors
		public SecurityValidation() {
			super();
		}
		public SecurityValidation(int userID) {
			this.userID = userID;
		}
		
		//Setters and Getters
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
		public String getCheckSum() {
			return checkSum;
		}
		public void setCheckSum(String checkSum) {
			this.checkSum = checkSum;
		}
		public String getExpirationDate() {
			return expirationDate;
		}
		public void setExpirationDate(String expirationDate) {
			this.expirationDate = expirationDate;
		}

		//Method for setting and retrieving the unique id
		public String generateCheckSum() {
			this.checkSum = UUID.randomUUID().toString().replace("-", "");
			return this.checkSum;
		}
}
