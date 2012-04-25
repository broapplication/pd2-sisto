package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.WorkflowReader;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ProcessImpl implements Process {
	protected Calendar startTime;
	protected List<ActionStatusReader> status;
	protected WorkflowReader workflow;
	
	protected void initProcess(Calendar startTime, WorkflowReader workflow, List<ActionStatusReader> status) {
		this.startTime=startTime;
		this.workflow=workflow;
		if(status==null)
			this.status=new LinkedList<ActionStatusReader>();
		else
			this.status=status;
	}
	
	public ProcessImpl() {
		initProcess(null, null, null);
	}
	
	
	public ProcessImpl(Calendar startTime, WorkflowReader workflow, List<ActionStatusReader> status) {
		initProcess(startTime, workflow, status);
	}

	@Override
	public Calendar getStartTime() {
		return (Calendar)startTime.clone();
	}

	@Override
	public List<ActionStatusReader> getStatus() {
		return status;
	}

	@Override
	public WorkflowReader getWorkflow() {
		return workflow;
	}
	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public void setWorkflow(WorkflowReader workflow) {
		this.workflow = workflow;
	}
	
	public void addActionStatus(ActionStatusReader actionStatus) {
		status.add(actionStatus);
	}
}
