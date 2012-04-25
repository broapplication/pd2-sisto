package it.polito.pd2.WF.sol5;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.WorkflowReader;

public class ActionReaderImpl	implements ActionReader {
	
    protected Boolean automaticallyInstantiated;
    protected String name;
    protected String role;

	protected WorkflowReaderImpl enclosingWorkflow;

	public ActionReaderImpl(String name) {
		this.name=name;
	}

	public ActionReaderImpl() {
	}

	@Override
    public boolean isAutomaticallyInstantiated() {
        if (automaticallyInstantiated == null)
            return false;
        else
            return automaticallyInstantiated.booleanValue();
    }
	
    public void setAutomaticallyIstantiated(Boolean value) {
        this.automaticallyInstantiated = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        this.role = value;
    }


	@Override
	public WorkflowReader getEnclosingWorkflow() {
		return enclosingWorkflow;
	}


	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((ActionReader)obj).getName());
	}

}
