package it.polito.pd2.WF.sol2;

import java.util.HashMap;

public class NextStatesMap extends HashMap<String, ParserState> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7167993756762895704L;
	public NextStatesMap() {
		ParserState s;
		put("RESET", s=new ParserState());
		
		put("STATEworkflowMonitor",s=new ParserState());
		s.permitEmptyInput();
		
		put("STATEworkflow",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("workflow", "STATEworkflow");
		
		put("STATEaction",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("action", "STATEaction");
		s.addMatch("process", "STATEprocess");
		
		put("STATEsimpleAction",s=new ParserState());
		s.permitEmptyInput();
		
		put("STATEprocessAction",s=new ParserState());
		s.permitEmptyInput();
		
		put("STATEprocess",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("process", "STATEprocess");
		
		put("STATEactionStatus",s=new ParserState());
		s.permitEmptyInput();
		s.addMatch("actionStatus", "STATEactionStatus");
		
		put("STATEactor",s=new ParserState());
		s.permitEmptyInput();
	}
}
