package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.WorkflowReader;

public abstract class ActionReaderImpl implements ActionReader {
	
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
	
	public ActionReaderImpl() {
		initAction("", "", null, false);
	}
	
	public ActionReaderImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated) {
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
