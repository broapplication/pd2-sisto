package it.polito.pd2.WF.sol6.client1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowReader;
import it.polito.pd2.WF.sol6.client1.gen.ActionType;
import it.polito.pd2.WF.sol6.client1.gen.WorkflowType;
import it.polito.pd2.WF.sol6.client1.gen.ProcessSummary;

public class WorkflowReaderImpl implements WorkflowReader {

	private Map<String, ActionReaderImpl> actions;
	private Map<String, List<SimpleActionReaderImpl>> actionRefs;
	private LinkedHashSet<ProcessReader> processes;
	private WorkflowMonitorImpl monitor;

	protected String name;


	public WorkflowReaderImpl() {
		actionRefs=new HashMap<String, List<SimpleActionReaderImpl>>();
		actions=new LinkedHashMap<String, ActionReaderImpl>();
		processes=new LinkedHashSet<ProcessReader>();
	}

	public WorkflowReaderImpl(WorkflowType w, WorkflowMonitorImpl monitor) {
		this();
		this.monitor=monitor;

		setName(w.getName());

		//add actions
		for(ActionType action : w.getAction())
			if(action.getWorkflow()==null)
				actions.put(action.getName(), new SimpleActionReaderImpl(action,this));
			else
				actions.put(action.getName(), new ProcessActionReaderImpl(action,this));
		
		linkActions();

		//add processes
		for(ProcessSummary process : w.getProcessSummary())
			processes.add(new ProcessReaderImpl(process, this));
	}

	public WorkflowReaderImpl(String name) {
		this.name=name;	//required by ProcessAction, only name needed
	}

	public ActionReaderImpl[] getActionList() {
		if (actions != null)
			return actions.values().toArray(new ActionReaderImpl[0]);
		return null;
	}
	
	public void setActionList(ActionReaderImpl[] v) {
		actions=new LinkedHashMap<String, ActionReaderImpl>();
		for(ActionReaderImpl a : v)
			actions.put(a.getName(), a);
	}


	@Override
	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}


	public void addActionRef(String key,SimpleActionReaderImpl ref) {
		WorkflowMonitorFactoryImpl.addRef(actionRefs, key, ref);
	}

	@Override
	public Set<ActionReader> getActions() {
		return new LinkedHashSet<ActionReader>(actions.values());
	}

	@Override
	public Set<ProcessReader> getProcesses() {
		return processes;
	}

	@Override
	public ActionReader getAction(String name) {
		return actions.get(name);
	}

	private void linkActions() {
		//link actions with SimpleAction.possibleNextActions in actionRefs
		
		for(String name:actionRefs.keySet()) {
			ActionReaderImpl action=actions.get(name);
			for(SimpleActionReaderImpl simple : actionRefs.get(name))
				simple.setPossibleNextAction(action);
		}
	}

	public WorkflowMonitorImpl getMonitor() {
		return monitor;
	}

}
