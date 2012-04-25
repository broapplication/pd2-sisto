package it.polito.pd2.WF.sol2;

public class ParserAttribute {

	private boolean required;
	private String pattern;
	private String keyset;
	private String keyref;
	private String selector;
	private boolean list;
	
	public ParserAttribute(boolean required, String pattern) {
		this(required, pattern, null, "", false,"");
	}
	
	public ParserAttribute(boolean required, String pattern, String keyset) {
		this(required, pattern, keyset, "", false,"");
	}
	
	public ParserAttribute(boolean required, String pattern, 
			String keyset, String keyref) {
		this(required, pattern, keyset, keyref, false,"");
	}
	
	public ParserAttribute(boolean required, String pattern, 
			String keyset, String keyref,boolean list) {
		this(required, pattern, keyset, keyref, list,"");
	}
	
	public ParserAttribute(boolean required, String pattern, 
			String keyset,String keyref,boolean list,String selector) {
		this.required = required;
		this.pattern = pattern;
		this.keyset = keyset;
		this.keyref=keyref;
		this.list=list;
		this.selector=selector;
	}
	
	public boolean isKey() {
		return keyset!=null && !keyset.equals("");
	}
	

	public String getKeyset() {
		return keyset;
	}
	
	public void setKeyset(String keyset) {
		this.keyset=keyset;
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getKeyref() {
		return keyref;
	}

	public void setKeyref(String keyref) {
		this.keyref = keyref;
	}

	public boolean isList() {
		return list;
	}

	public void setList(boolean list) {
		this.list = list;
	}

	public boolean isKeyref() {
		return keyref!=null && !keyref.equals("");
	}
	
	public boolean isSelector() {
		return selector!=null && !selector.equals("");
	}
	
	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

}
