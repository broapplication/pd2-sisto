package it.polito.pd2.WF.sol2;
import it.polito.pd2.WF.WorkflowReader;

public class ProcessActionImpl extends ActionImpl implements ProcessAction {
	protected WorkflowReader actionWorkflow;
	
	protected void initProcessAction(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,WorkflowReader actionWorkflow) {
		initAction(name, role, enclosingWorkflow, automaticallyInstantiated);
		this.actionWorkflow=actionWorkflow;
	}
	public ProcessActionImpl() {
		initProcessAction("", "", null, false, null);
	}
	
	public ProcessActionImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,WorkflowReader actionWorkflow) {
		initProcessAction(name, role, enclosingWorkflow, automaticallyInstantiated, actionWorkflow);
	}
	
	@Override
	public WorkflowReader getActionWorkflow() {
		return actionWorkflow;
	}
}
