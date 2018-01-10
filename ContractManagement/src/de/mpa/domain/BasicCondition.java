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

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int basicConditionId;
	private String location;
	private int radius;
	private LocalDate startDate;
	private LocalDate endDate;
	private int estimatedWorkload;
	private double fee;
	private LocalDate timestamp;
	// ---------------------

	// Constructor to build a basic condition
	public BasicCondition() {
		super();
		this.timestamp = LocalDate.now();
	}
	// --------------------------------------

	// Setter and getter
	@XmlElement
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@XmlElement
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@XmlElement
	public int getBasicConditionId() {
		return basicConditionId;
	}
	
	@XmlElement
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = LocalDate.parse(startDate);
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@XmlElement
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = LocalDate.parse(endDate);
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@XmlElement
	public int getEstimatedWorkload() {
		return estimatedWorkload;
	}

	public void setEstimatedWorkload(int estimatedWorkload) {
		this.estimatedWorkload = estimatedWorkload;
	}

	@XmlElement
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@XmlElement
	public LocalDate getTimestamp() {
		return timestamp;
	}


}
