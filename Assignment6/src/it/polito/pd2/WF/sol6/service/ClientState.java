package it.polito.pd2.WF.sol6.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClientState {
	
	private Calendar lastRequestTime;
	private Set<Integer> requests;
	
	public ClientState() {
		lastRequestTime=null;
		requests=Collections.synchronizedSet(new HashSet<Integer>());
	}
	
	public boolean addRequest(int request) {
		if(requests.add(request)) {
			lastRequestTime=Calendar.getInstance();
			return true;
		}
		return false;
	}
	
	public boolean hasRequest(int request) {
		return requests.contains(request);
	}

	public Calendar getLastRequestTime() {
		return lastRequestTime;
	}

}
