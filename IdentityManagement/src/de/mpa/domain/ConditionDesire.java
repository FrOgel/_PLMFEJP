package de.mpa.domain;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private Date earliestStartDate;
	private Date earliestEndDate;
	@OneToOne(cascade = CascadeType.ALL)
	private GeographicalCondition place;
	
	//Setter and getter
	public Date getStartDate() {
		return earliestStartDate;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse("2011-02-10");
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        
        System.out.println(sql);
	}
	
	public void setStartDate(String startDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed;
		try {
			parsed = format.parse(startDate);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			this.earliestStartDate = sql;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void setStartDate(Date startDate) {
		this.earliestStartDate = startDate;
	}

	@XmlElement
	public Date getEndDate() {
		return earliestEndDate;
	}

	public void setEndDate(String endDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed;
		try {
			parsed = format.parse(endDate);
			java.sql.Date sql = new java.sql.Date(parsed.getTime());
			this.earliestEndDate = sql;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEndDate(Date endDate) {
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
