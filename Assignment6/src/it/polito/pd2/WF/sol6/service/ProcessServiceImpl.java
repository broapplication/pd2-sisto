package it.polito.pd2.WF.sol6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Errors;
import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Messages;
import it.polito.pd2.WF.sol6.service.gen.ActionFault;
import it.polito.pd2.WF.sol6.service.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.service.gen.ActionType;
import it.polito.pd2.WF.sol6.service.gen.ActorFault;
import it.polito.pd2.WF.sol6.service.gen.ActorType;
import it.polito.pd2.WF.sol6.service.gen.DifferentRole;
import it.polito.pd2.WF.sol6.service.gen.DifferentRole_Exception;
import it.polito.pd2.WF.sol6.service.gen.GenericFault_Exception;
import it.polito.pd2.WF.sol6.service.gen.ProcessPortType;
import it.polito.pd2.WF.sol6.service.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.service.gen.TerminateActionResponse;
import it.polito.pd2.WF.sol6.service.gen.UnknownCode;
import it.polito.pd2.WF.sol6.service.gen.UnknownNames;
import it.polito.pd2.WF.sol6.service.gen.UnknownNames_Exception;

import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

@WebService(
		name="ProcessPortType", 
		endpointInterface="it.polito.pd2.WF.sol6.service.gen.ProcessPortType", 
		wsdlLocation="META-INF/wsService.wsdl", 
		targetNamespace="http://pad.polito.it/Workflow", 
		serviceName="ProcessService", portName="ProcessPort")
public class ProcessServiceImpl implements ProcessPortType{

	private WorkflowServer server;
	private static Logger logger = Logger.getLogger(WorkflowInfoServiceImpl.class.getName());
	
	public ProcessServiceImpl() {
		server=WorkflowServer.getInstance();
	}
	
	
	@Override
	public ActionStatusType takeInCharge(String clientID, int requestID, String pCode,
			String aCode, ActorType actor) throws ActionFault, ActorFault,
			DifferentRole_Exception, GenericFault_Exception, UnknownCode {
		if(clientID==null || pCode==null || aCode==null || actor==null)
			throw new GenericFault_Exception(
					Messages.REQUEST_NOT_WELL_FORMED, 
					Errors.REQUEST_NOT_WELL_FORMED);
		try {
			if(server.getClientState(clientID) == null) {
				logger.warning(Messages.UNKNOWN_CLIENT_ID);
				throw new GenericFault_Exception(
						Messages.UNKNOWN_CLIENT_ID, 
						Errors.UNKNOWN_CLIENT_ID);
			}
			
			server.serveRequest(clientID, requestID);
			
			if(!server.checkActor(actor)) {
				logger.warning(Messages.INVALID_ACTOR);
				throw new ActorFault(
						Messages.INVALID_ACTOR, 
						Errors.INVALID_ACTOR);
			}

			ProcessSummary process = server.getProcesses().get(pCode);
			if(process == null)
				throw new UnknownCode(
						Messages.UNKNOWN_CODE, 
						Errors.UNKNOWN_CODE);
			
			for(ActionStatusType status : process.getAvailableActionStatus()) {
				if(!status.getACode().equals(aCode))
					continue;
				
				if(!status.getAction().getRole().equals(actor.getRole())) {
					DifferentRole err = new DifferentRole();
					err.setExpected(status.getAction().getRole());
					err.setReceived(actor.getRole());
					throw new DifferentRole_Exception(Messages.DIFFERENT_ROLE, err);
				}
				
				return server.takeInChargeAction(pCode, status, actor);
					
			}
			//action not found
			throw new ActionFault(
					Messages.UNAVAILABLE_ACTION, 
					Errors.UNAVAILABLE_ACTION);
			

		} catch (WorkflowServerException e) {
			logger.warning(e.getMessage());
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
		
	}
	
	@Override
	public String takeClientID() throws GenericFault_Exception {
		try {
			return server.takeClientID();
		} catch (WorkflowServerException e) {
			logger.warning(Messages.SERVER_NOT_READY);
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
	}

	@Override
	public void terminateAction(String clientID, int requestID, String pCode,
			String aCode, List<String> nextAction,
			Holder<XMLGregorianCalendar> terminationTime,
			Holder<ProcessSummary> nextProcess,
			Holder<List<ActionStatusType>> nextActionStatus)
			throws ActionFault, GenericFault_Exception, UnknownCode,
			UnknownNames_Exception {
		if(clientID==null || pCode==null || aCode==null)
			throw new GenericFault_Exception(
					Messages.REQUEST_NOT_WELL_FORMED, 
					Errors.REQUEST_NOT_WELL_FORMED);
		try {
			if(server.getClientState(clientID) == null) {
				logger.warning(Messages.UNKNOWN_CLIENT_ID);
				throw new GenericFault_Exception(
						Messages.UNKNOWN_CLIENT_ID, 
						Errors.UNKNOWN_CLIENT_ID);
			}
			
			server.serveRequest(clientID, requestID);
			
			ProcessSummary process = server.getProcesses().get(pCode);
			if(process == null)
				throw new UnknownCode(
						Messages.UNKNOWN_CODE, 
						Errors.UNKNOWN_CODE);
			
			for(ActionStatusType status : process.getActiveActionStatus()) {
				if(!status.getACode().equals(aCode))
					continue;

				ActionType action = status.getAction();
				if(nextAction != null) {
					List<String> checkNext = new ArrayList<String>(nextAction);
					if(!checkNext.isEmpty()) {
						WorkflowUtilities.splitList(checkNext, action.getNextAction());
						if(!checkNext.isEmpty()) {
							//error next actions
							UnknownNames errNames = Errors.errorNextActions();
							errNames.getNames().addAll(checkNext);
							throw new UnknownNames_Exception(
									Messages.ERROR_NEXT_ACTIONS, 
									errNames);
						}
					}
				}
				
				TerminateActionResponse res = server.terminateAction(pCode, status, nextAction);
				nextProcess.value=res.getNextProcess();
				nextActionStatus.value=res.getNextActionStatus();
				terminationTime.value=res.getTerminationTime();
				return;
			}
			
			//action not found
			throw new ActionFault(
					Messages.UNAVAILABLE_ACTION, 
					Errors.UNAVAILABLE_ACTION);

		} catch (WorkflowServerException e) {
			logger.warning(e.getMessage());
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
		
	}
}
