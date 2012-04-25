package it.polito.pd2.WF.sol6.client1;

import java.util.Calendar;

import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.Actor;
import it.polito.pd2.WF.sol6.client1.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.client1.gen.ActorType;

public class ActionStatusReaderImpl implements ActionStatusReader {

	private Calendar terminationTime=null;
	private Actor actor=null;
	private String actionName;

	public ActionStatusReaderImpl(ActionStatusType status) {
		if(status.getAction() != null)
			this.actionName=status.getAction().getName();
		else
			this.actionName=status.getName();
		
		if(status.getTerminationTime() != null)
			this.terminationTime=status.getTerminationTime().toGregorianCalendar();
		
		ActorType actor=status.getActor();
		if(actor != null)
			this.actor=new Actor(actor.getName(),actor.getRole());
	}
	
	@Override
	public String getActionName() {
		return actionName;
	}

	@Override
	public Actor getActor() {
		return actor;
	}

	@Override
	public Calendar getTerminationTime() {
		return terminationTime;
	}

	@Override
	public boolean isTakenInCharge() {
		return actor != null;
	}

	@Override
	public boolean isTerminated() {
		return terminationTime != null;
	}

}
