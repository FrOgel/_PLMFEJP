package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rank {
	//Attribute declaration
	private int rankID;
	private int UserID;
	private float percentage;
	//---------------------
	
	
	//Constructor to build a term
	public Rank(int rankID, int userID, float percentage) {
		this.rankID = rankID;
		UserID = userID;
		this.percentage = percentage;
	}
	//---------------------------

	
	//Setter and getter
	@XmlElement
	public int getRankID() {
		return rankID;
	}

	public void setRankID(int rankID) {
		this.rankID = rankID;
	}

	@XmlElement
	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	@XmlElement
	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	//-----------------
}
