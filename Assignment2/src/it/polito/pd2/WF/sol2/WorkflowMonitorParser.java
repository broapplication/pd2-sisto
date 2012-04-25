package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.Actor;
import it.polito.pd2.WF.WorkflowMonitor;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TimeZone;

public class WorkflowMonitorParser extends DefaultHandler {
	
/*---------PARSER GENERAL FIELDS----------------*/
	//errors
	private static final String ERROR_ID = "error id";
	private static final String ERROR_IDREF = "error idref";
	private static final String ERROR_ATTRIBUTE = "error attribute";
	private static final String ERROR_UNEXPECTED_ELEMENT = "error unexpected element";
	private static final String ERROR_EXPECTED_ATTRIBUTE = "error expected attribute";
	private static final String ERROR_UNEXPECTED_ATTRIBUTE = "error unexpected attribute";
	private static final String ERROR_PATTERN = "error pattern";
	private static final String ERROR_UNEXPECTED_STATE = "error unexpected state";
	private static final String ERROR_DATE = "error date format";

	private static final String ERROR_UNEXPECTED_EMPTY = "error unexpected empty";
	private static final String ERROR_TEXT = "error text";

	//FSN names
	private static final String FIRST_CHILD = "FIRST_CHILD";
	private static final String NEXT = "NEXT";

	//states
	public final Map<String,Map<String, ParserState>> states=initStates();
	//keysets
	private final Map<String, ParserKeyset> keysets=new KeysetsMap();
	//selectors
	private final Map<String,ParserSelector> selectors=new SelectorsMap();
	//elements schema
	private final Map<String, ParserElement> elements=new ElementsMap();
	
	//parser fields
	private Stack<String> stack;
	private String currentState;
	private String fsn;
	private String text;	
	private Map<String, Map<String,String>> attrMap; //<Element,<Attibute,Value>>	
	private Map<String, String> textMap;	//<Element,Text>
	
	private boolean debug=true;

/*---------PARSER SPECIFIC FIELDS---------------*/

	private WorkflowMonitorImpl monitor;
	WorkflowImpl wf;		//last read
	ProcessImpl proc;		//last read
	ActionStatusImpl as;	//last read

/*-------------CONSTRUCTOR----------------------*/

	public WorkflowMonitorParser(String file) throws SAXException, IOException, ParserConfigurationException {
		attrMap=new HashMap<String, Map<String,String>>();
		textMap=new HashMap<String, String>();

		monitor=new WorkflowMonitorImpl();

		//parse file
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser saxParser = spf.newSAXParser();
		saxParser.parse(new File(file), this);
	}

/*--------------PARSER GENERAL METHODS-------------------------------*/
	
	private Map<String, Map<String, ParserState>> initStates() {
		Map<String,Map<String,ParserState>> m=new HashMap<String, Map<String,ParserState>>();
		m.put(FIRST_CHILD, new FirstChildStateMap());
		m.put(NEXT, new NextStatesMap());
		return m;
	}
	@Override
	public void startDocument() throws SAXException {
		if(debug) {
			System.out.println("Start");
			System.out.println(currentState+":"+fsn);
		}
		stack=new Stack<String>();
		currentState="RESET";
		fsn=FIRST_CHILD;
		text="";
	}

	@Override
	public void endDocument() throws SAXException {
		Set<String> idrefs=new LinkedHashSet<String>();
		for(String keyset : keysets.keySet()) {
			idrefs.addAll(keysets.get(keyset).getRefs());
		}

		if(!idrefs.isEmpty())
			throw new SAXException(ERROR_IDREF + " on " + idrefs.toString());

		currentState=stack.pop();
		if(debug)
			System.out.println(currentState+":"+fsn);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		String nextState;
		if(debug)
			System.out.println("trovato <"+qName+">");

		//VERIFICA MODALITA' MISTA
		if(!text.trim().equals(""))
			errorMixedMode();

		//VERIFICA SE ATTESO
		try {
			nextState=states.get(fsn).get(currentState).getOutput(qName);
			if(nextState==null)
				throw errorElement(ERROR_UNEXPECTED_ELEMENT, qName);
		} catch (NullPointerException npe) {
			throw new SAXException(ERROR_UNEXPECTED_STATE + currentState);
		}

		//VERIFICA ATTRIBUTI
		checkAttributes(qName,attributes);

		//INIZIALIZZA ELEMENTO
		initElement(qName); 

		//PROSSIMO STATO
		if(fsn==FIRST_CHILD)
			stack.push(currentState);
		currentState=nextState;

		fsn=FIRST_CHILD;
		if(debug)
			System.out.println(currentState+":"+fsn);

	}	

	private Calendar parseTime(String toParse) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
		Calendar g=new GregorianCalendar();
		g.setTime(dateFormat.parse(toParse));
		g.setTimeZone(TimeZone.getTimeZone(toParse.substring("dd/MM/yyyy HH:mm ".length())));
		
		return g;
	}

	private void checkText (String element) throws SAXException {
		if(!text.trim().matches(elements.get(element).getText()))
			throw errorElement(ERROR_TEXT, element);
		textMap.put(element, text.trim());
	}

	private void checkAttributes (String element,Attributes attributes) throws SAXException {

		try {
			Map<String, ParserAttribute> schemas=elements.get(element).getAttributes();
			Map<String, String> output=new HashMap<String, String>();
			attrMap.put(element, output);
			int length=attributes.getLength();

			for(int i=0;i<length;i++) {		//-------scan attributes
				//check attribute i
				String attribName=attributes.getQName(i);
				String attribValue=attributes.getValue(i);
				ParserAttribute attribSchema=schemas.get(attribName);
				if(attribSchema==null)
					throw errorAttribute(ERROR_UNEXPECTED_ATTRIBUTE,element,attribName);

				//check keyset				
				if(attribSchema.isKey()) {
					ParserKeyset keyset = keysets.get(attribSchema.getKeyset());
					if(keyset.hasKey(attribValue))
						throw errorAttribute(ERROR_ID,element, attribName);
					keyset.addKey(attribValue);
					keyset.removeRef(attribValue);
				}

				//if selector, change scope
				ParserSelector selector;
				if(attribSchema.isSelector()) {
					selector=selectors.get(attribSchema.getSelector());
					keysets.put(selector.getTarget(),selector.select(attribValue));
					if(debug)
						System.out.println("KEYSET "+selector.getTarget()+": "+keysets.get(selector.getTarget()));
				}


				String[] pieces={attribValue};
				if(attribSchema.isList())
					pieces = attribValue.split("\\s");


				for (String piece : pieces) {
					//check pattern
					if(!piece.matches(attribSchema.getPattern()))
						throw errorAttribute(ERROR_PATTERN,element, attribName);

					//check keyref
					if(attribSchema.isKeyref()) {
						ParserKeyset keyset=keysets.get(attribSchema.getKeyref());
						if(!keyset.hasKey(piece))
							keyset.addRef(piece);
					}
				}

				//remove checked attribute
				schemas.remove(attribName);
				output.put(attribName, attribValue);
			}	//-----------------scan attributes

			//check other attributes in schemas
			for(String otherAttrib: schemas.keySet()) {
				if(schemas.get(otherAttrib).isRequired())
					throw errorAttribute(ERROR_EXPECTED_ATTRIBUTE,element,otherAttrib);
			}


		} catch (NullPointerException npe) {
			throw errorElement(ERROR_ATTRIBUTE, element);
		}


	}

	private static SAXException errorElement(String error,String element) {
		return new SAXException(error+" on "+element);
	}


	private static SAXException errorAttribute(String error,String element,String attribute) {
		return new SAXException(error +" on " + element + "/@" + attribute);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(debug)
			System.out.println("finito </"+qName+">");

		if(fsn.equals(FIRST_CHILD)) {
			if(debug)
				System.out.println("nessun figlio");

			//NO CHILD
			if(!states.get(fsn).get(currentState).hasEmptyInput())
				throw errorElement(ERROR_UNEXPECTED_EMPTY, qName + " first child");

		} else {
			//VERIFICA MODALITA' MISTA
			if(!text.trim().equals(""))
				errorMixedMode();
		}

		if(fsn.equals(NEXT)) {
			if(debug)
				System.out.println("nessun next");

			//NO NEXT
			if(!states.get(fsn).get(currentState).hasEmptyInput())
				throw errorElement(ERROR_UNEXPECTED_EMPTY, qName + " next child");
		}

		//CONTROLLI SUL TESTO
		checkText(qName);
		if(!text.trim().equals("")) {
			if(debug)
				System.out.println("Testo: " + text.trim());
		}


		//TERMINA ELEMENTO
		completeElement(qName);


		text="";

		//PROSSIMO STATO
		if(fsn.equals(NEXT))
			currentState=stack.pop();
		fsn=NEXT;
		if(debug)
			System.out.println(currentState+":"+fsn);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(length > 0) {
			String t = new String(ch, start, length).trim();
			if(t.length()>0)
				text=text.concat(" ").concat(t);
		}
	}

	private void errorMixedMode() throws SAXException {
		fatalError(new SAXParseException("mixed mode", null));
	}


	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		super.fatalError(e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		throw new SAXException(e);
	}



/*---------------------PARSER SPECIFIC METHODS----------------------------*/
	
	
	

	public WorkflowMonitor getWorkflowMonitor() {
		return monitor;
	}
	
	private void initElement(String element) throws SAXException {
		Map<String,String> wfAttributes=attrMap.get("workflow");
		Map<String,String> actAttributes=attrMap.get("action");
		Map<String,String> sactAttributes=attrMap.get("simpleAction");
		Map<String,String> pactAttributes=attrMap.get("processAction");
		Map<String,String> procAttributes=attrMap.get("process");
		Map<String,String> asAttributes=attrMap.get("actionStatus");
		Map<String,String> arAttributes=attrMap.get("actor");


		ActionImpl act;
		SimpleActionImpl sact;
		ProcessActionImpl pact;
		
		Actor ar;
		boolean auto;
		String name;
		if(element=="workflow") {
			name=wfAttributes.get("name");
			wf=(WorkflowImpl) monitor.getWorkflow(name);
			if(wf==null) {
				wf=new WorkflowImpl(name,null);
				monitor.addWorkflow(name, wf);
			}

		} else if(element=="action") {

		} else if(element=="simpleAction") {
			//create action and resolve links
			name=actAttributes.get("name");
			if(actAttributes.get("automaticallyInstantiated")!=null)
				auto=actAttributes.get("automaticallyInstantiated").equals("true");
			else
				auto=false;
			sact=new SimpleActionImpl(name,	actAttributes.get("role"),wf,auto);			
			wf.addAction(name, sact);	//resolve links

			//possible next actions
			if(sactAttributes.get("possibleNextActions")!=null) {
				String[] idrefs=sactAttributes.get("possibleNextActions").split("\\s");
				for (String idref : idrefs) {
					ActionReader next = wf.getAction(idref);
					if(next!=null)
						sact.addPossibleNextAction(next);
					else
						wf.addActionLink(idref, sact);
				}
			}			

		} else if(element=="processAction") {
			//create action and resolve links
			name=actAttributes.get("name");
			if(actAttributes.get("automaticallyInstantiated")!=null)
				auto=actAttributes.get("automaticallyInstantiated").equals("true");
			else
				auto=false;
			String actionWfName = pactAttributes.get("actionWorkflow");
			WorkflowImpl actionWf = (WorkflowImpl) monitor.getWorkflow(actionWfName);
			if(actionWf==null) {
				actionWf=new WorkflowImpl(actionWfName,null);
				monitor.addWorkflow(actionWfName, actionWf);
			}
			pact=new ProcessActionImpl(name,actAttributes.get("role"),wf,auto,actionWf);			
			wf.addAction(name, pact);	//resolve links

		} else if(element=="process") {
			try {
				proc=new ProcessImpl(parseTime(procAttributes.get("startTime")), wf, null);
			} catch (ParseException e) {
				throw errorAttribute(ERROR_DATE, "process", "startTime");			
			}
			wf.addProcess(proc);
			monitor.addProcess(proc);

		} else if(element=="actionStatus") {
			name=asAttributes.get("action");
			act=(ActionImpl) wf.getAction(name);
			String terminationTime=asAttributes.get("terminationTime");
			if(terminationTime==null)
				as=new ActionStatusImpl(act);
			else {
				try {
					as=new ActionStatusImpl(act, null, parseTime(asAttributes.get("terminationTime")));
				} catch (ParseException pe) {
					throw errorAttribute(ERROR_DATE, "actionStatus", "terminationTime");
				}
			}
			proc.addActionStatus(as);

		} else if(element=="actor") {
			name=arAttributes.get("name");
			ar=new Actor(name, arAttributes.get("role"));
			as.setActor(ar);

		} else {
			errorElement(ERROR_UNEXPECTED_ELEMENT, element);

		}
	}
	
	private void completeElement(String qName) {
	}
		
}