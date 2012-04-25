package it.polito.pd2.WF.sol2;

import java.util.HashMap;
import java.util.Map;

public class ParserState {
	Map<String, String> matches;
	private boolean emptyInput;
	
	public ParserState(boolean emptyInput, Map<String, String> matches) {
		this.setEmptyInput(emptyInput);
		this.matches=matches;
	}
	
	public ParserState() {
		setEmptyInput(false);
		matches=new HashMap<String, String>();
	}
	
	public void permitEmptyInput() {
		emptyInput=true;
	}

	public String getOutput(String input) {
		if(matches==null || input==null)
			return null;
		return matches.get(input);
	}
	
	public void addMatch(String input, String output) {
		matches.put(input,output);
	}

	public boolean hasEmptyInput() {
		return emptyInput;
	}

	public void setEmptyInput(boolean emptyInput) {
		this.emptyInput = emptyInput;
	}
	
}
