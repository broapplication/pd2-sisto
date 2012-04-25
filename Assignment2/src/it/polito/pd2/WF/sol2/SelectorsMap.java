package it.polito.pd2.WF.sol2;

import java.util.HashMap;

public class SelectorsMap extends HashMap<String, ParserSelector> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8446799795304489980L;

	public SelectorsMap() {
		put("workflowAction",new ParserSelector("actName"));
	}
}
