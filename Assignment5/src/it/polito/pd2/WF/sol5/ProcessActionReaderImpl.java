package it.polito.pd2.WF.sol5;

import it.polito.pd2.WF.ProcessActionReader;
import it.polito.pd2.WF.WorkflowReader;
import it.polito.pd2.WF.sol5.gen.Action;

public class ProcessActionReaderImpl extends ActionReaderImpl implements ProcessActionReader {
	protected WorkflowReaderImpl actionWorkflow;

	public ProcessActionReaderImpl(Action action, WorkflowReaderImpl workflow) {
		if(action==null)
			return;
		setName(action.getName());
		setRole(action.getRole());
		setAutomaticallyIstantiated(action.isAutomaticallyInstantiated());
		enclosingWorkflow=workflow;
		workflow.getMonitor().addWorkflowRef(action.getWorkflow(), this);
	}

	public ProcessActionReaderImpl() {
	}

	@Override
	public WorkflowReader getActionWorkflow() {
		return actionWorkflow;
	}

	public void setActionWorkflow(WorkflowReaderImpl value) {
		this.actionWorkflow = value;
	}
}
