package de.mpa.domain;

import java.time.LocalDate;
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
	private LocalDate startDate;
	private LocalDate endDate;
	private int estimatedWorkload;
	//---------------------
	
	//Constructor to build a basic condition
	public BasicCondition() {
		super();
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
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	@XmlElement
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate localDate) {
		this.endDate = localDate;
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
