package it.polito.pd2.WF.sol6.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import it.polito.pd2.WF.sol6.service.gen.ActionFieldType;
import it.polito.pd2.WF.sol6.service.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.service.gen.ActionsField;
import it.polito.pd2.WF.sol6.service.gen.ActorType;
import it.polito.pd2.WF.sol6.service.gen.GenericFault;
import it.polito.pd2.WF.sol6.service.gen.GetProcessSummaries.Fields;
import it.polito.pd2.WF.sol6.service.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.service.gen.UnknownCodes;
import it.polito.pd2.WF.sol6.service.gen.UnknownNames;
import it.polito.pd2.WF.sol6.service.gen.UnknownRoles;

public abstract class WorkflowUtilities {
	
	public static final String REGEX_ID="^[a-zA-Z]\\w*";
	public static final String REGEX_ALPHA="^[a-zA-Z]$|^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$";

	public static class Errors {
		public static final GenericFault SERVICE_UNAVAILABLE=new GenericFault(){{
			setErrno(0x01);
			setMessage(WorkflowUtilities.Messages.SERVICE_UNAVAILABLE);
		}};
		
		public static final GenericFault REQUEST_NOT_WELL_FORMED=new GenericFault(){{
			setErrno(0x02);
			setMessage(WorkflowUtilities.Messages.REQUEST_NOT_WELL_FORMED);
		}};
		public static final GenericFault UNKNOWN_NAME=new GenericFault(){{
			setErrno(0x11);
			setMessage(WorkflowUtilities.Messages.UNKNOWN_NAME);
		}};
		public static final GenericFault UNKNOWN_CODE=new GenericFault(){{
			setErrno(0x12);
			setMessage(WorkflowUtilities.Messages.UNKNOWN_CODE);
		}};
		public static final GenericFault UNKNOWN_ROLE=new GenericFault(){{
			setErrno(0x13);
			setMessage(WorkflowUtilities.Messages.UNKNOWN_CODE);
		}};
		public static UnknownNames unknownNames() {
			UnknownNames err=new UnknownNames();
			err.setMessage(WorkflowUtilities.Messages.UNKNOWN_NAMES);
			return err;
		}
		public static UnknownCodes unknownCodes() {
			UnknownCodes err=new UnknownCodes();
			err.setMessage(WorkflowUtilities.Messages.UNKNOWN_CODES);
			return err;
		}
		public static UnknownRoles unknownRoles() {
			UnknownRoles err=new UnknownRoles();
			err.setMessage(WorkflowUtilities.Messages.UNKNOWN_ROLES);
			return err;
		}
		public static final GenericFault UNKNOWN_CLIENT_ID=new GenericFault(){{
			setErrno(0x21);
			setMessage(WorkflowUtilities.Messages.UNKNOWN_CLIENT_ID);
		}};
		public static final GenericFault UNAVAILABLE_ACTION=new GenericFault(){{
			setErrno(0x31);
			setMessage(WorkflowUtilities.Messages.UNAVAILABLE_ACTION);
		}};
		public static final GenericFault INVALID_ACTOR=new GenericFault(){{
			setErrno(0x32);
			setMessage(WorkflowUtilities.Messages.INVALID_ACTOR);
		}};
		public static UnknownNames errorNextActions() {
			UnknownNames err=new UnknownNames();
			err.setMessage(WorkflowUtilities.Messages.ERROR_NEXT_ACTIONS);
			return err;
		}
		
		
		
	}

	public static class Messages {
		static String settingUp(String service) {
			return service + " is setting up...";
		}
		
		public static final String SERVER_NOT_READY = "Server not ready";
		public static final String SERVICE_UNAVAILABLE = "Service unavailable";
		public static final String REQUEST_NOT_WELL_FORMED = "Request not well-formed";
		public static final String UNKNOWN_NAMES = "Unknown names";
		public static final String UNKNOWN_CODES = "Unknown codes";
		public static final String UNKNOWN_ROLES = "Unknown roles";
		public static final String UNKNOWN_NAME = "Unknown name";
		public static final String UNKNOWN_CODE = "Unknown code";
		public static final String UNKNOWN_ROLE = "Unknown role";
		public static final String UNKNOWN_CLIENT_ID = "Unknown client id";
		public static final String REQUEST_ALREADY_SERVED = "Request already served";
		public static final String UNAVAILABLE_ACTION = "Unavailable action";
		public static final String DIFFERENT_ROLE = "Different role";
		public static final String INVALID_ACTOR = "Invalid actor";
		public static final String INVALID_PROCESS = "Invalid process";
		public static final String ERROR_NEXT_ACTIONS = "Error next actions";
		
		public static String SETTING_UP_ERR = "Errore nel caricamento dei servizi.";
		public static final String SETTING_UP_OK = "Servizi caricati correttamente.";
		public static String workflowsOk(String list) {
			return "Workflow caricati correttamente: " + list;
		}
		public static final String REQUEST_WHEN_NOT_READY = "Richiesta al server non pronto.";
		public static final String UNEXPECTED_SERVER_ERROR = "Unexpected error on server";
		
		
		
		
		
	}

	public static ProcessSummary cloneProcessSummary(ProcessSummary process) {
		ProcessSummary res=new ProcessSummary();
		res.setPCode(process.getPCode());
		res.setStartTime(process.getStartTime());
		res.setWorkflowName(process.getWorkflowName());
		res.setActions(process.getActions());
		res.getActionStatus().addAll(process.getActionStatus());
		res.setActiveActions(process.getActiveActions());
		res.getActiveActionStatus().addAll(process.getActiveActionStatus());
		res.setAvailableActions(process.getAvailableActions());
		res.getAvailableActionStatus().addAll(process.getAvailableActionStatus());
		res.setTerminatedActions(process.getTerminatedActions());
		res.getTerminatedActionStatus().addAll(process.getTerminatedActionStatus());
		return res;
	}
	
	public static ActionStatusType cloneActionStatus(ActionStatusType status) {
		ActionStatusType res=new ActionStatusType();
		res.setACode(status.getACode());
		res.setAction(status.getAction());
		res.setActor(status.getActor());
		res.setName(status.getName());
		res.setTerminationTime(status.getTerminationTime());
		return res;
	}

	static void modifyProcessSummaryFields(ProcessSummary process, Fields fields) {
		//server stored processes have all fields and embedded actions
		
		//default fields
		boolean workflowName=true;
		boolean startTime=true;
		ActionsField actions=ActionsField.LIST;
		ActionsField availableActions=ActionsField.LENGTH;
		ActionsField activeActions=ActionsField.LENGTH;
		ActionsField terminatedActions=ActionsField.LENGTH;
		boolean actionFieldEmbedded=true;
		
		if(fields != null) {
			if(fields.isWorkflowName() != null)
				workflowName=fields.isWorkflowName();
			if(fields.isStartTime() != null)
				startTime=fields.isStartTime();
			if(fields.getActions() != null)
				actions=fields.getActions();
			if(fields.getAvailableActions() != null)
				availableActions=fields.getAvailableActions();
			if(fields.getActiveActions() != null)
				activeActions=fields.getActiveActions();
			if(fields.getTerminatedActions() != null)
				terminatedActions=fields.getTerminatedActions();
			if(fields.getActionFieldType() != null && 
					fields.getActionFieldType().equals(ActionFieldType.ACTION))
				actionFieldEmbedded=true;
		}
		
		if(!workflowName)
			process.setWorkflowName(null);
		if(!startTime)
			process.setStartTime(null);
		
		List<ActionStatusType> status=process.getActionStatus();
		process.setActions(status.size());
		if(actions==ActionsField.LENGTH)
			status.clear();
		else if(!actionFieldEmbedded)
			detachActionFromStatus(status);
		
		status=process.getAvailableActionStatus();
		process.setAvailableActions(status.size());
		if(availableActions==ActionsField.LENGTH)
			status.clear();
		else if(!actionFieldEmbedded)
			detachActionFromStatus(status);
		
		status=process.getActiveActionStatus();
		process.setActiveActions(status.size());
		if(activeActions==ActionsField.LENGTH)
			status.clear();
		else if(!actionFieldEmbedded)
			detachActionFromStatus(status);
		
		status=process.getTerminatedActionStatus();
		process.setTerminatedActions(status.size());
		if(terminatedActions==ActionsField.LENGTH)
			status.clear();
		else if(!actionFieldEmbedded)
			detachActionFromStatus(status);
	}

	static void detachActionFromStatus(List<ActionStatusType> list) {
		//-------------REBUILD STATUS---------------------
		List<ActionStatusType> newList = new ArrayList<ActionStatusType>();
		ActionStatusType newStatus;
		for(ActionStatusType status : list) {
			newStatus=cloneActionStatus(status);
			newStatus.setAction(null);
			newList.add(newStatus);
		}
		list.clear();
		list.addAll(newList);
	}

	static <E> HashSet<E> splitList(Collection<E> toSplit, Collection<E> toCheckInto) {
		//commonPart = toSplit INTERSECT toCheckInto
		HashSet<E> commonPart=new HashSet<E>(toCheckInto);
		commonPart.retainAll(toSplit);
		//toSplit = toSplit MINUS commonPart
		toSplit.removeAll(commonPart);
		return commonPart;
	}
	
	public static boolean containsActor(HashSet<ActorTypeHashable> actors,	ActorType actor) {
		ActorTypeHashable act = new ActorTypeHashable();
		act.setName(actor.getName());
		act.setRole(actor.getRole());
		return actors.contains(act);
	}

}
