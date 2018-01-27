package de.mpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@NamedQuery(query = "SELECT ct FROM ContractTemplate ct WHERE ct.userId = :userId", name = "get principal contract templates")
@XmlRootElement
public class ContractTemplate {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int templateId;
	private int userId;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Term> terms = new ArrayList<Term>();
	@OneToOne(cascade = CascadeType.ALL)
	private PlaceOfPerformance placeOfPerformance;
	private String templateName;
	
	@XmlElement
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	@XmlElement
	public List<Term> getTerms() {
		return terms;
	}
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	@XmlElement
	public PlaceOfPerformance getPlaceOfPerformance() {
		return placeOfPerformance;
	}
	public void setPlaceOfPerformance(PlaceOfPerformance placeOfPerformance) {
		this.placeOfPerformance = placeOfPerformance;
	}
	@XmlElement
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	@XmlElement
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
