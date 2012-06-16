package it.polito.pd2.WF.sol2;

import java.util.Calendar;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.Actor;
import it.polito.pd2.WF.WorkflowMonitorError;

public class ActionStatusReaderImpl implements ActionStatusReader {
	private static final String ERROR_ROLE = "error different role";
	
	protected ActionReader action;
	protected Actor actor;
	protected Calendar terminationTime;
	
	protected void initActionStatus(ActionReader action, Actor actor, Calendar terminationTime) {
		this.action=action;
		setActor(actor);
		this.terminationTime=terminationTime;
	}
	
	public ActionStatusReaderImpl(ActionReader action) {
		initActionStatus(action, null, null);		
	}
	
	public ActionStatusReaderImpl(ActionReader action,Actor actor) {
		initActionStatus(action, actor, null);
	}
	
	public ActionStatusReaderImpl(ActionReader action,Actor actor,Calendar terminationTime) {
		initActionStatus(action, actor, terminationTime);
	}

	@Override
	public String getActionName() {
		return action.getName();
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
		return actor!=null;
	}

	@Override
	public boolean isTerminated() {
		return terminationTime!=null;
	}
	
	public void setActor(Actor actor) {
		if(actor!=null) {
			if(!actor.getRole().equals(action.getRole()))
				throw new WorkflowMonitorError(
						ERROR_ROLE + " between " +action.getName()+" and "+actor.getName());
			this.actor=actor;
		}
	}
	
	public ActionReader getAction() {
		return action;
	}

	public void setAction(ActionReader action) {
		this.action = action;
	}
}
