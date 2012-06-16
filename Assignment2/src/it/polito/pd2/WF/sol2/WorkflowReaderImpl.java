package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

public class WorkflowReaderImpl implements WorkflowReader {
	
	private String name;
	protected Map<String, ActionReader> actions;
	protected Set<ProcessReader> processes;
	private Map<String, Set<SimpleActionReaderImpl>> actionsIdrefs;
	
	private void initWorkflow(String name,Collection<ActionReader> actions,Collection<ProcessReader> processes) {
		this.setName(name);
		this.actions=new LinkedHashMap<String, ActionReader>();
		if(actions!=null)
			for (ActionReader act : actions) {
				this.actions.put(act.getName(), act);
			}
		this.processes=(processes!= null ? new LinkedHashSet<ProcessReader>(processes) : new LinkedHashSet<ProcessReader>());
		actionsIdrefs=new HashMap<String, Set<SimpleActionReaderImpl>>();
	}
	
	public WorkflowReaderImpl() {
		//create unlinked workflow
		initWorkflow("", null, null);	
	}
	
	public WorkflowReaderImpl(String name,Collection<ActionReader> actions) {
		initWorkflow(name, actions, null);
	}
	
	public WorkflowReaderImpl(String name,Collection<ActionReader> actions,Collection<ProcessReader> processes) {
		initWorkflow(name, actions, processes);
	}
	
	private void linkActionToIdrefs(String idref,ActionReader act) throws SAXException {
		if(actionsIdrefs.get(idref)==null)
			return;
		for(SimpleActionReaderImpl sa: actionsIdrefs.get(idref)) {
			sa.addPossibleNextAction(act);
		}

	}
	
	public void addActionLink(String idref, SimpleActionReaderImpl action) {
		if(!actionsIdrefs.containsKey(idref))
			actionsIdrefs.put(idref, new HashSet<SimpleActionReaderImpl>());
		actionsIdrefs.get(idref).add(action);

	}

	@Override
	public ActionReader getAction(String name) {
		return name!=null ?	actions.get(name) : null;
	}

	@Override
	public Set<ActionReader> getActions() {
		return new LinkedHashSet<ActionReader>(actions.values());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<ProcessReader> getProcesses() {
		return new LinkedHashSet<ProcessReader>(processes);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addAction(String name,ActionReader action) throws SAXException {
		actions.put(name, action);
		linkActionToIdrefs(name, action);
	}
	
	public void addProcess(ProcessReader process) {
		processes.add(process);
	}
}
