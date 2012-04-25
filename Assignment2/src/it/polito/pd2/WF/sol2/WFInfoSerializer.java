package it.polito.pd2.WF.sol2;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.Actor;
import it.polito.pd2.WF.ProcessActionReader;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.SimpleActionReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.WorkflowReader;

public class WFInfoSerializer {
	private static final String DTD_FILE = "wfInfo.dtd";
	
	WorkflowMonitor monitor;
	private DateFormat dateFormat; 
	
	protected Document doc;	// document element
	protected Element root;	// document root element
	
	private Map<WorkflowReader, Element> wfMap;
	private Map<ProcessReader, Element> procMap;
	private Map<ActionReader, Element> actMap;
	
	public static void main(String args[])
	{		
        if (args.length != 1) {
            System.err.println("Usage: java WFInfoSerializer filename");
            System.exit(1);
        }

		try {
			WorkflowMonitorFactory WMfactory = WorkflowMonitorFactory.newInstance();
			WorkflowMonitor monitor = WMfactory.newWorkflowMonitor();
			
			WFInfoSerializer wf = new WFInfoSerializer(monitor);
			wf.serialize(new PrintStream(args[0]));;
			wf.serialize(System.out);
		} catch (ParserConfigurationException pce) {
			System.out.println("Parser configuration exception: " + pce.getMessage());
            System.exit(1);
		} catch (TransformerException te) {
			System.out.println("Transformer exception: " + te.getMessage());
			System.exit(1);
		} catch (FileNotFoundException fnfe) {
			System.out.println("File not found exception: " + fnfe.getMessage());
			System.exit(1);
		} catch (NullPointerException npe) {
			System.out.println("Null pointer exception: " + npe.getMessage());
			System.exit(1);
		}
		
	}
	
	public WFInfoSerializer(WorkflowMonitor monitor) throws ParserConfigurationException
	{
		if(monitor==null)
			return;
		this.monitor=monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
		wfMap = new LinkedHashMap<WorkflowReader, Element>();
		procMap = new LinkedHashMap<ProcessReader, Element>();
		actMap =new LinkedHashMap<ActionReader, Element>();		
		initDOM();
	}

	public void serialize(PrintStream out) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance ();
		tf.setAttribute("indent-number", new Integer(3));
		
		Transformer t = tf.newTransformer ();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		

		t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DTD_FILE);
		Source input = new DOMSource (doc);
		Result output;
		try {
			output = new StreamResult(new OutputStreamWriter(out, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new TransformerException(e);
		}

		t.transform (input, output);
	}

	private Element getProcessElement(ProcessReader p)	{
		Element e=procMap.get(p);
		if(e==null)	{
			e=doc.createElement("process");
			dateFormat.setTimeZone(p.getStartTime().getTimeZone());
			e.setAttribute("startTime", dateFormat.format(p.getStartTime().getTime()));
			procMap.put(p, e);
		}
		return e;
	}
	
	private Element getWorkflowElement(WorkflowReader w) {
		Element e=wfMap.get(w);
		if(e==null)	{
			e=doc.createElement("workflow");
			e.setAttribute("name", w.getName());
			wfMap.put(w, e);
		}
		return e;
	}
	
	
	private Element getActionElement(ActionReader a) {
		Element e=actMap.get(a);
		if(e==null)	{
			e=doc.createElement("action");
			e.setAttribute("name", a.getName());
			e.setAttribute("role", a.getRole());
			e.setAttribute("automaticallyIstantiated",
				a.isAutomaticallyInstantiated()?"true":"false");
			actMap.put(a, e);
		}
		return e;
	}
	
	private Element getActorElement(Actor a) {
		Element e=doc.createElement("actor");
		e.setAttribute("name", a.getName());
		e.setAttribute("role", a.getRole());
		return e;
	}
	
	private void initDOM() throws ParserConfigurationException {
		DocumentBuilderFactory DOMfactory = DocumentBuilderFactory.newInstance();
		// factory.setNamespaceAware (true);
		DocumentBuilder builder = DOMfactory.newDocumentBuilder();

		// Create the document
		doc = builder.newDocument();

		// Create and append the root element
		root = (Element) doc.createElement("workflowMonitor");
		doc.appendChild(root);
		
		//scan WORKFLOWS##########################################
		Set<WorkflowReader> wfset=monitor.getWorkflows();
		for (WorkflowReader wfr: wfset) {
			//new workflow
			Element wf=getWorkflowElement(wfr);
			//scan workflow's ACTIONS-----------------------------------
			Set<ActionReader> arset=wfr.getActions();
			for(ActionReader ar: arset){
				//new action
				Element act=getActionElement(ar);
				//simple/process action
				if (ar instanceof SimpleActionReader) {
					Element simple=doc.createElement("simpleAction");
					//scan next actions
					Set<ActionReader> setNxt = ((SimpleActionReader)ar).getPossibleNextActions();
					if(!setNxt.isEmpty()){
						StringBuffer sb=new StringBuffer();
						for (ActionReader nAct: setNxt)
							sb.append(getActionElement(nAct).getAttribute("name")+" ");
						simple.setAttribute("possibleNextActions",sb.toString().trim());
					}
					act.appendChild(simple);
				}
				else if (ar instanceof ProcessActionReader) {
					Element proc=doc.createElement("processAction");
					//next workflow
					proc.setAttribute("actionWorkflow",
							getWorkflowElement(	
								((ProcessActionReader)ar).getActionWorkflow()).getAttribute("name"));
					act.appendChild(proc);
				}
				wf.appendChild(act);
			}//end scan actions-----------------------------------
				
			//scan PROCESSES***************************************
			Set<ProcessReader> prset = wfr.getProcesses();
			
			for (ProcessReader prr: prset) {
				//new process
				Element pr=getProcessElement(prr);
				//scan action status
				List<ActionStatusReader> statusSet = prr.getStatus();
				for (ActionStatusReader asr : statusSet) {
					//new action status
					Element as=doc.createElement("actionStatus");
					as.setAttribute("action", asr.getActionName());
					if (asr.isTakenInCharge())
						as.appendChild(getActorElement(asr.getActor()));

					if (asr.isTerminated()) {
						dateFormat.setTimeZone(asr.getTerminationTime().getTimeZone());
						as.setAttribute("terminationTime",
								dateFormat.format(asr.getTerminationTime().getTime()));
					}
					pr.appendChild(as);
				}
				wf.appendChild(pr);
			}//end scan processes************************************
			root.appendChild(wf);
		}
	}
}
