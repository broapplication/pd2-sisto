package it.polito.pd2.WF.sol2;
import it.polito.pd2.WF.ProcessActionReader;
import it.polito.pd2.WF.WorkflowReader;

public class ProcessActionReaderImpl extends ActionReaderImpl implements ProcessActionReader {
	protected WorkflowReader actionWorkflow;
	
	protected void initProcessAction(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,WorkflowReader actionWorkflow) {
		initAction(name, role, enclosingWorkflow, automaticallyInstantiated);
		this.actionWorkflow=actionWorkflow;
	}
	public ProcessActionReaderImpl() {
		initProcessAction("", "", null, false, null);
	}
	
	public ProcessActionReaderImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,WorkflowReader actionWorkflow) {
		initProcessAction(name, role, enclosingWorkflow, automaticallyInstantiated, actionWorkflow);
	}
	
	@Override
	public WorkflowReader getActionWorkflow() {
		return actionWorkflow;
	}
}
