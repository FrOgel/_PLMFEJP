package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 	frank.vogel
 * Date: 	07.01.2018
 * Purpose:	Class for adding the sender and receiver of a condition offer to the basic conditions
 */

@Entity
@XmlRootElement
public class NegotiationCondition{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int negotiationConditionId;
	private BasicCondition condition;
	private int senderId;
	private int receiverId;
	
	public NegotiationCondition() {
		super();
	}
	
	@XmlElement
	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	@XmlElement
	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	
	@XmlElement
	public BasicCondition getCondition() {
		return condition;
	}

	public void setCondition(BasicCondition condition) {
		this.condition = condition;
	}

	@XmlElement
	public int getNegotiationConditionId() {
		return negotiationConditionId;
	}

	public void setNegotiationConditionId(int negotiationConditionId) {
		this.negotiationConditionId = negotiationConditionId;
	}

}
