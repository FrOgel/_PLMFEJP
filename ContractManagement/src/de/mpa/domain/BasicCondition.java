package de.mpa.domain;

import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*The main purpose of this class is to provide an uniform possibility to compare the conditions of a contract with the 
 * condition desire of a possible client.
 */
@Entity
@XmlRootElement
public class BasicCondition {
	
	//Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int BasicConditionID;
	private String location;
	private String radius;
	private Date startDate;
	private Date endDate;
	private int estimatedWorkload;
	//---------------------
	
	//Constructor to build a basic condition
	public BasicCondition() {
		super();
	}
	public BasicCondition(String location, String radius, Date startDate, Date endDate, int estimatedWorkload) {
		super();
		this.location = location;
		this.radius = radius;
		this.startDate = startDate;
		this.endDate = endDate;
		this.estimatedWorkload = estimatedWorkload;
	}
	//--------------------------------------
	
	//Setter and getter
	@XmlElement
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@XmlElement
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	@XmlElement
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@XmlElement
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@XmlElement
	public int getEstimatedWorkload() {
		return estimatedWorkload;
	}
	public void setEstimatedWorkload(int estimatedWorkload) {
		this.estimatedWorkload = estimatedWorkload;
	}
	//-----------------
}
