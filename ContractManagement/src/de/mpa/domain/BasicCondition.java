package de.mpa.domain;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*The main purpose of this class is to provide an uniform possibility to compare the conditions of a contract with the 
 * condition desire of a possible client.
 */
@Entity
@NamedQuery(query = "SELECT b FROM BasicCondition b", name = "get all conditions")
@XmlRootElement
public class BasicCondition {

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int basicConditionId;
	private int estimatedWorkload;
	private double fee;
	private boolean teleWorkPossible;
	private Date startDate;
	private Date endDate;
	private LocalDate timestamp;
	@OneToOne(cascade = CascadeType.ALL)
	private PlaceOfPerformance placeOfPerformance;
	// ---------------------

	// Constructor to build a basic condition
	public BasicCondition() {
		super();
		this.timestamp = LocalDate.now();
	}
	// --------------------------------------

	// Setter and getter
	@XmlElement
	public int getBasicConditionId() {
		return basicConditionId;
	}
	
	@XmlElement
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed;
		try {
			parsed = format.parse(startDate);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			this.startDate = sql;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@XmlElement
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed;
		try {
			parsed = format.parse(endDate);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			this.endDate = sql;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@XmlElement
	public PlaceOfPerformance getPlaceOfPerformance() {
		return placeOfPerformance;
	}
	
	public void setPlaceOfPerformance(PlaceOfPerformance placeOfPerformance) {
		this.placeOfPerformance = placeOfPerformance;
	}

	
	@XmlElement
	public boolean isTeleWorkPossible() {
		return teleWorkPossible;
	}

	
	public void setTeleWorkPossible(boolean teleWorkPossible) {
		this.teleWorkPossible = teleWorkPossible;
	}

}
