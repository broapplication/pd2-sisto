package it.polito.pd2.WF.sol6.client1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowReader;
import it.polito.pd2.WF.sol6.client1.gen.WorkflowType;

public class WorkflowMonitorImpl implements	WorkflowMonitor {
	
	private Map<String, WorkflowReaderImpl> workflows;
	private Map<String, List<ProcessActionReaderImpl>> workflowRefs;
		

	public WorkflowMonitorImpl(List<WorkflowType> workflowList) throws WorkflowMonitorError {
		workflows=new LinkedHashMap<String, WorkflowReaderImpl>();
		workflowRefs=new HashMap<String, List<ProcessActionReaderImpl>>();
		if(workflowList==null)
			return;

		//add workflows
		for(WorkflowType w : workflowList)
			workflows.put(w.getName(),new WorkflowReaderImpl(w,this));

		linkWorkflows();
	}


	public void addWorkflowRef(String key, ProcessActionReaderImpl ref) {
		WorkflowMonitorFactoryImpl.addRef(workflowRefs, key, ref);
	}
	
	
	@Override
	public Set<WorkflowReader> getWorkflows() {
		return new LinkedHashSet<WorkflowReader>(workflows.values());
	}

	@Override
	public WorkflowReader getWorkflow(String name) {
		return workflows.get(name);
	}

	@Override
	public Set<ProcessReader> getProcesses() {		
		Set<ProcessReader> processes = new LinkedHashSet<ProcessReader>();
		for(WorkflowReaderImpl workflow : workflows.values())
			processes.addAll(workflow.getProcesses());
		return processes;
	}

	private void linkWorkflows() {
		for(String name:workflowRefs.keySet()) {
			WorkflowReaderImpl workflow=workflows.get(name);
			for(ProcessActionReaderImpl action: workflowRefs.get(name))
				action.setActionWorkflow(workflow);
		}
		
	}
}
