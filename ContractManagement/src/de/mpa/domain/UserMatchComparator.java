package de.mpa.domain;

import java.util.Comparator;

public class UserMatchComparator implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		
		UserMatch m1 = (UserMatch) o1;
		UserMatch m2 = (UserMatch) o2;
		
		int compare = m1.compareTo(m2);
		
		if(compare==0) {
			if(m1.getContractId()==m2.getContractId())
				compare = 0;
			
			if(m1.getContractId()<m2.getContractId())
				compare = -1;
			
			if(m1.getContractId()>m2.getContractId())
				compare = 1;
		}
		
		return compare;
	}
}
