package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author 	frank.vogel
 * Date: 	07.01.2018
 * Purpose:	Class for adding the sender and receiver of a condition offer to the basic conditions
 */

public class ConditionNegotiation {
	
	private BasicCondition conditionOffer;
	private int senderId;
	private int receiverId;
	
	public ConditionNegotiation() {
		super();
	}

	@XmlElement
	public BasicCondition getConditionOffer() {
		return conditionOffer;
	}

	public void setConditionOffer(BasicCondition conditionOffer) {
		this.conditionOffer = conditionOffer;
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

}
