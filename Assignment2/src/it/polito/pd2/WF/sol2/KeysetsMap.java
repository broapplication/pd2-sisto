package it.polito.pd2.WF.sol2;

import java.util.HashMap;

public class KeysetsMap extends HashMap<String, ParserKeyset> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2711008706628204608L;
	public KeysetsMap() {
		put("wfName",new ParserKeyset());
		put("actName",new ParserKeyset());
	}

}
