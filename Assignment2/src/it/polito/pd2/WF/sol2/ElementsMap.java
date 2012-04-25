package it.polito.pd2.WF.sol2;

import java.util.HashMap;

public class ElementsMap extends HashMap<String, ParserElement> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2173775334797082378L;
	private static final String REGEX_ID="^[a-zA-Z]\\w*";
	private static final String REGEX_ALPHA="^[a-zA-Z]$|^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$";
	private static final String REGEX_TRUE_FALSE="true|false";
	private static final String REGEX_DATE="(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)[0-9]{2} (2[0-3]|[0-1][0-9]):[0-5][0-9] .*";
	
	public ElementsMap() {
		ParserElement e;
		put("workflowMonitor", e=new ParserElement());
		
		put("workflow",	e=new ParserElement());
		e.addAttribute("name", new ParserAttribute(true, REGEX_ID, "wfName"));
		e.getAttribute("name").setSelector("workflowAction");
		
		put("action", e=new ParserElement());
		e.addAttribute("name", new ParserAttribute(true, REGEX_ID, "actName"));
		e.addAttribute("automaticallyIstantiated", new ParserAttribute(false, REGEX_TRUE_FALSE));
		e.addAttribute("role", new ParserAttribute(true, REGEX_ALPHA));
		
		
		put("simpleAction", e=new ParserElement());
		e.addAttribute("possibleNextActions", new ParserAttribute(false, REGEX_ID, null, "actName", true));
		
		put("processAction", e=new ParserElement());
		e.addAttribute("actionWorkflow", new ParserAttribute(true, REGEX_ID, null, "wfName"));
		
		put("process",	e=new ParserElement());
		e.addAttribute("startTime", new ParserAttribute(true, REGEX_DATE));
		
		put("actionStatus", e=new ParserElement());
		e.addAttribute("action", new ParserAttribute(true, REGEX_ID, null, "actName"));
		e.addAttribute("terminationTime", new ParserAttribute(false, REGEX_DATE));
		
		put("actor", e=new ParserElement());
		e.addAttribute("name", new ParserAttribute(true, REGEX_ALPHA));
		e.addAttribute("role", new ParserAttribute(true, REGEX_ALPHA));
	}

}
