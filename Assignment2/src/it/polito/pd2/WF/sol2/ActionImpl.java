package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.WorkflowReader;

public abstract class ActionImpl implements Action {
	
	protected WorkflowReader enclosingWorkflow;
	protected String name;
	protected String role;
	protected boolean automaticallyInstantiated;
	
	protected void initAction(String name, String role, WorkflowReader enclosingWorkflow, boolean automaticallyInstantiated)
	{
		this.name=(name!=null ? name : "");
		this.role=(role!=null ? role : "");
		this.enclosingWorkflow=enclosingWorkflow;
		this.automaticallyInstantiated=automaticallyInstantiated;
	}
	
	public ActionImpl() {
		initAction("", "", null, false);
	}
	
	public ActionImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated) {
		initAction(name, role, enclosingWorkflow, automaticallyInstantiated);
	}
	
	@Override
	public WorkflowReader getEnclosingWorkflow() {
		return enclosingWorkflow;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public boolean isAutomaticallyInstantiated() {
		return automaticallyInstantiated;
	}

}
