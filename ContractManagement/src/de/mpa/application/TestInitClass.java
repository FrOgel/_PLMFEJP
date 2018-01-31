package de.mpa.application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.ContractType;
import de.mpa.domain.RequirementCriteriaType;
import de.mpa.domain.PlaceOfPerformance;
import de.mpa.domain.Requirement;
import de.mpa.infrastructure.PersistenceContract;

public class TestInitClass {

	//Generates test data
	public static void main(String[] args) {
		Contract c = new Contract();
		BasicCondition b = new BasicCondition();
		PlaceOfPerformance p = new PlaceOfPerformance();
		Requirement r = new Requirement();
		PersistenceContract pc = new PersistenceContract();

		for (int i = 1; i <= 10; i++) {

			String country = "";
			String city = "";
			String zipCode = "";

			if ((i % 2) == 0) {
				country = "Germany";
				city = "Heilbronn";
				zipCode = "74076";
			} else {
				country = "Germany";
				city = "Stuttgart";
				zipCode = "70174";
			}

			b.setEndDate("2018-01-01");
			b.setStartDate("2018-01-31");
			b.setEstimatedWorkload(ThreadLocalRandom.current().nextInt(10, 1000 + 1));
			b.setTeleWorkPossible(true);
			b.setFee(round(ThreadLocalRandom.current().nextDouble(10, 1000000 + 1), 2));

			p.setCountry(country);
			p.setPlace(city);
			p.setZipCode(zipCode);

			c.setBasicConditions(b);
			c.setPlaceOfPerformance(p);
			c.setName("Test Contract " + i);
			c.setContractState(ContractState.PUBLISHED);
			c.setPrincipalID((i % 10) + 1);
			c.setSubject("Subject description comes here " + i);
			c.setType(ContractType.DEVELOPMENT);

			c = (Contract) pc.addObjectToPersistance(c);

			List<Requirement> rList = new ArrayList<Requirement>();

			for (int j = 1; j <= 6; j++) {
				switch (j) {
				case 1:
					r.setDescription("Java");
					break;
				case 2:
					r.setDescription("Java EE");
					break;
				case 3:
					r.setDescription("Javascript");
					break;
				case 4:
					r.setDescription("C++");
					break;
				case 5:
					r.setDescription("MySql");
					break;
				case 6:
					r.setDescription("C#");
					break;
				}
				
				r.setCriteriaType(RequirementCriteriaType.MANDATORY);
				rList.add(r);
				r = new Requirement();
			}

			c.setRequirementsProfile(rList);
			pc.updateExistingObject(c);

			rList = new ArrayList<Requirement>();
			c = new Contract();
			b = new BasicCondition();
			p = new PlaceOfPerformance();

		}

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
