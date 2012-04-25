package it.polito.pd2.WF.sol2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowReader;

public class WorkflowMonitorImpl implements WorkflowMonitor {	
	
	private Map<String, WorkflowReader> workflows;
	private Set<ProcessReader> processes;
	private Map<String, Set<ActionStatusImpl>> actorsIdrefs;
	
	public WorkflowMonitorImpl() {
	
		workflows=new LinkedHashMap<String, WorkflowReader>();
		processes=new LinkedHashSet<ProcessReader>();
		actorsIdrefs=new HashMap<String, Set<ActionStatusImpl>>();

	}
	
	public void addActorLink(String idref, ActionStatusImpl actionStatus) {
		if(!actorsIdrefs.containsKey(idref))
			actorsIdrefs.put(idref, new HashSet<ActionStatusImpl>());
		actorsIdrefs.get(idref).add(actionStatus);

	}
	
	public void addProcess(ProcessReader process) {
		processes.add(process);
	}
	
	public void addWorkflow(String name, WorkflowReader workflow) {
		workflows.put(name, workflow);
	}
	

	@Override
	public Set<ProcessReader> getProcesses() {
		return new LinkedHashSet<ProcessReader>(processes);
	}

	@Override
	public WorkflowReader getWorkflow(String name) {
		return workflows.get(name);
	}

	@Override
	public Set<WorkflowReader> getWorkflows() {
		return new LinkedHashSet<WorkflowReader>(workflows.values());
	}
}
