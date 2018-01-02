package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement
public class Address {
		
		//Attribute declaration
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int addressId;
		private String zipCode; 
		private String country, state, city, street, houseNumber;

		
		//Constructor
		public Address() {
			
		}		
		public Address(String country, String state, String zipCode, String city, String street, String houseNumber) {
			super();
			this.country = country;
			this.state = state;
			this.zipCode = zipCode;
			this.city = city;
			this.street = street;
			this.houseNumber = houseNumber;
		}
		//---------------------------------------
		
		//Setter and getter
		@XmlElement
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		@XmlElement
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		@XmlElement
		public String getZipCode() {
			return zipCode;
		}
		public void setZipCode(String zipCode) {
			this.zipCode = zipCode;
		}
		@XmlElement
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		@XmlElement
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		@XmlElement
		public String getHouseNumber() {
			return houseNumber;
		}
		public void setHouseNumber(String houseNumber) {
			this.houseNumber = houseNumber;
		}
		//------------------------------------------------
}
