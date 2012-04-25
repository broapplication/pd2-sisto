package it.polito.pd2.WF.sol2;

import java.util.HashMap;
import java.util.Map;

public class ParserSelector {
	private Map<String,ParserKeyset> keysets;
	private String target;
	
	public ParserSelector(String target) {
		keysets=new HashMap<String, ParserKeyset>();
		this.target=target;
	}
	
	public ParserKeyset select(String key) {
		ParserKeyset k=keysets.get(key);
		if(k==null) {
			k=new ParserKeyset();
			keysets.put(key, k);
		}
		return k;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
