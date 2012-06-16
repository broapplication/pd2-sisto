package it.polito.pd2.WF.sol2;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.SimpleActionReader;
import it.polito.pd2.WF.WorkflowReader;

import java.util.LinkedHashSet;
import java.util.Set;

public class SimpleActionReaderImpl extends ActionReaderImpl implements SimpleActionReader {
	protected Set<ActionReader> possibleNextActions;
	
	protected void initSimpleAction(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,Set<ActionReader> possibleNextActions) {
		initAction(name, role, enclosingWorkflow, automaticallyInstantiated);
		this.possibleNextActions=(possibleNextActions!=null ? 
				new LinkedHashSet<ActionReader>(possibleNextActions) :
				new LinkedHashSet<ActionReader>());
	}
	
	public SimpleActionReaderImpl() {
		initSimpleAction("", "", null, false, null);
		
	}
	
	public SimpleActionReaderImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated) {
		initSimpleAction(name, role, enclosingWorkflow, automaticallyInstantiated, null);
	}
	
	public SimpleActionReaderImpl(String name,String role,WorkflowReader enclosingWorkflow,boolean automaticallyInstantiated,Set<ActionReader> possibleNextActions) {
		initSimpleAction(name, role, enclosingWorkflow, automaticallyInstantiated, possibleNextActions);
	}
	
	
	@Override
	public Set<ActionReader> getPossibleNextActions() {
		return possibleNextActions;
	}
	
	public void addPossibleNextAction(ActionReader action) {
		possibleNextActions.add(action);
	}

}
