package it.polito.pd2.WF.sol2;

import java.util.HashMap;

public class FirstChildStateMap extends HashMap<String, ParserState> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6019447888297839867L;

	public FirstChildStateMap() {
		ParserState s;
		put("RESET", s=new ParserState());
		s.addMatch("workflowMonitor", "STATEworkflowMonitor");
		
		put("STATEworkflowMonitor",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("workflow", "STATEworkflow");
		
		put("STATEworkflow",s=new ParserState());
		s.addMatch("action", "STATEaction");
		
		put("STATEaction",s=new ParserState());
		s.addMatch("simpleAction", "STATEsimpleAction");
		s.addMatch("processAction", "STATEprocessAction");
		
		put("STATEsimpleAction",s=new ParserState());
		s.permitEmptyInput();
		
		put("STATEprocessAction",s=new ParserState());
		s.permitEmptyInput();
		
		put("STATEprocess",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("actionStatus", "STATEactionStatus");
		
		put("STATEactionStatus",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("actor", "STATEactor");
		
		put("STATEactor",s=new ParserState());
		s.permitEmptyInput();
	}
}
