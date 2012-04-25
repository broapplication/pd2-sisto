package it.polito.pd2.WF.sol2;

import java.util.HashSet;
import java.util.Set;

public class ParserKeyset {
	private Set<String> keys;
	private Set<String> refs;
	
	@Override
	public String toString() {
	
		return keys.toString();
	}
	
	public ParserKeyset() {
		keys=new HashSet<String>();
		refs=new HashSet<String>();
	}
	
	public boolean hasKey(String key) {
		if(key==null)
			return false;
		return keys.contains(key);
	}
	
	public Set<String> getRefs() {
		return new HashSet<String>(refs);
	}
	
	public void addKey(String key) {
		if(key!=null)
			keys.add(key);
	}
	
	public void removeKey(String key) {
		if(key!=null)
			keys.remove(key);
	}
	
	public void addRef(String ref) {
		if(ref!=null)
			refs.add(ref);
	}
	
	public void removeRef(String ref) {
		if(ref!=null)
			refs.remove(ref);
	}
	
}
