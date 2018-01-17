package de.mpa.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ConditionDesire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int desireId;
	private int maxWorkload;
	private double minFee;
	private LocalDate earliestStartDate;
	private LocalDate earliestEndDate;
	@OneToOne(cascade = CascadeType.ALL)
	private GeographicalCondition place;
	
	//Setter and getter
	public LocalDate getStartDate() {
		return earliestStartDate;
	}

	public void setStartDate(String startDate) {
		this.earliestStartDate = LocalDate.parse(startDate);
	}
	
	public void setStartDate(LocalDate startDate) {
		this.earliestStartDate = startDate;
	}

	@XmlElement
	public LocalDate getEndDate() {
		return earliestEndDate;
	}

	public void setEndDate(String endDate) {
		this.earliestEndDate = LocalDate.parse(endDate);
	}
	
	public void setEndDate(LocalDate endDate) {
		this.earliestEndDate = endDate;
	}

	@XmlElement
	public int getMaxWorkload() {
		return maxWorkload;
	}

	public void setMaxWorkload(int estimatedWorkload) {
		this.maxWorkload = estimatedWorkload;
	}

	@XmlElement
	public double getMinFee() {
		return minFee;
	}

	public void setMinFee(double fee) {
		this.minFee = fee;
	}

	
	@XmlElement
	public GeographicalCondition getPlace() {
		return place;
	}

	
	public void setPlace(GeographicalCondition place) {
		this.place = place;
	}
	
}
