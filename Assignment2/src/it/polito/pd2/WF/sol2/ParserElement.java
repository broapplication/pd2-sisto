package it.polito.pd2.WF.sol2;

import java.util.HashMap;
import java.util.Map;

public class ParserElement {
	private Map<String, ParserAttribute> attributes;
	private String text;
	
	public ParserElement() {
		attributes=new HashMap<String, ParserAttribute>();
		text="";
	}
	
	public ParserElement(String text) {
		attributes=new HashMap<String, ParserAttribute>();
		this.text=text;
	}
	
	public ParserElement(Map<String, ParserAttribute> attributes) {
		this.attributes = attributes;
		text="";
	}
	
	public ParserAttribute getAttribute(String name) {
		return attributes.get(name);
	}
	
	public void setAttribute(String name, boolean required, String pattern,String keyset,String keyref, boolean list,String selector) {
		attributes.put(name, new ParserAttribute(required, pattern, keyset, keyref, list, selector));
	}
	
	public int numAttributes() {
		if(attributes==null)
			return 0;
		else
			return attributes.size();
	}
	
	public Map<String, ParserAttribute> getAttributes() {
		if(attributes==null)
			return new HashMap<String, ParserAttribute>();
		else
			return new HashMap<String, ParserAttribute>(attributes);
	}
	
	public void addAttribute(String name,ParserAttribute attribute) {
		attributes.put(name, attribute);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
