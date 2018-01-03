package de.mpa.domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ContractState {
	NOT_PUBLISHED, PUBLISHED, IN_PROGRESS, FINISHED;
}
