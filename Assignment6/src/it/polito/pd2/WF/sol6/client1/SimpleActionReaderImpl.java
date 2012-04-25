package it.polito.pd2.WF.sol6.client1;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.SimpleActionReader;
import it.polito.pd2.WF.sol6.client1.gen.ActionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SimpleActionReaderImpl extends ActionReaderImpl implements SimpleActionReader {

	protected List<ActionReaderImpl> possibleNextActionList;


	public SimpleActionReaderImpl(ActionType action, WorkflowReaderImpl workflow) {
		if(action==null)
			return;
		setName(action.getName());
		setRole(action.getRole());
		setAutomaticallyIstantiated(action.isAutomaticallyInstantiated());
		enclosingWorkflow=workflow;

		List<ActionReaderImpl> nextList = getPossibleNextActionList();
		//add next actions
		for(String name : action.getNextAction()) {
			nextList.add(new ActionReaderImpl(name)); //will be replaced after mapping
			workflow.addActionRef(name,this);
		}

	}

	public SimpleActionReaderImpl() {
	}
	
	public List<ActionReaderImpl> getPossibleNextActionList() {
		if (possibleNextActionList == null) {
			possibleNextActionList = new ArrayList<ActionReaderImpl>();
		}
		return this.possibleNextActionList;
	}

	@Override
	public Set<ActionReader> getPossibleNextActions() {
		return new LinkedHashSet<ActionReader>(Arrays.asList(getPossibleNextActionList().toArray(new ActionReader[0])));
	}


	public void setPossibleNextAction(ActionReaderImpl realA) {
		getPossibleNextActionList().set(possibleNextActionList.indexOf(realA), realA);
	}

}
