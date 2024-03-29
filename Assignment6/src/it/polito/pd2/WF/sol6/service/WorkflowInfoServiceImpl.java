package it.polito.pd2.WF.sol6.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jws.WebService;

import it.polito.pd2.WF.sol6.service.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.service.gen.ActorType;
import it.polito.pd2.WF.sol6.service.gen.GenericFault_Exception;
import it.polito.pd2.WF.sol6.service.gen.GetProcessSummaries.Fields;
import it.polito.pd2.WF.sol6.service.gen.GetProcessSummaries.Restrictions;
import it.polito.pd2.WF.sol6.service.gen.ProcessFieldType;
import it.polito.pd2.WF.sol6.service.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.service.gen.UnknownCodes;
import it.polito.pd2.WF.sol6.service.gen.UnknownCodes_Exception;
import it.polito.pd2.WF.sol6.service.gen.UnknownNames;
import it.polito.pd2.WF.sol6.service.gen.UnknownNames_Exception;
import it.polito.pd2.WF.sol6.service.gen.UnknownRoles;
import it.polito.pd2.WF.sol6.service.gen.UnknownRoles_Exception;
import it.polito.pd2.WF.sol6.service.gen.WorkflowInfoPortType;
import it.polito.pd2.WF.sol6.service.gen.WorkflowType;

import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Errors;
import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Messages;

@WebService(
		name="WorkflowInfoPortType", 
		endpointInterface="it.polito.pd2.WF.sol6.service.gen.WorkflowInfoPortType", 
		wsdlLocation="META-INF/wsService.wsdl",
		targetNamespace="http://pad.polito.it/Workflow", 
		serviceName="WorkflowInfoService", portName="WorkflowInfoPort")
public class WorkflowInfoServiceImpl implements WorkflowInfoPortType {
	

	private WorkflowServer server;
	private static Logger logger = Logger.getLogger(WorkflowInfoServiceImpl.class.getName());
	
	public WorkflowInfoServiceImpl() {
		server=WorkflowServer.getInstance();
	}
	
	@Override
	public List<String> getRoles() throws GenericFault_Exception {
		try {
			return new ArrayList<String>(server.getRoles());
		} catch (WorkflowServerException e) {
			logger.warning(Messages.SERVER_NOT_READY);
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
	}

	@Override
	public List<String> getWorkflowNames() throws GenericFault_Exception {
		try {
			return new ArrayList<String>(server.getWorkflows().keySet());
		} catch (WorkflowServerException e) {
			logger.warning(Messages.SERVER_NOT_READY);
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
	}

	@Override
	public List<WorkflowType> getWorkflows(List<String> name,
			ProcessFieldType processField) throws GenericFault_Exception,
			UnknownNames_Exception {
		
		//-------CHECK FOR ERRORS--------------------------
		
		if((processField != null && !processField.equals(ProcessFieldType.P_CODE) &&
				!processField.equals(ProcessFieldType.PROCESS_SUMMARY)) || name == null) {
			logger.warning(Messages.REQUEST_NOT_WELL_FORMED);
			throw new GenericFault_Exception(
					Messages.REQUEST_NOT_WELL_FORMED,
					Errors.REQUEST_NOT_WELL_FORMED);
		}
		
		Map<String, WorkflowType> workflows;
		try {
			workflows = server.getWorkflows();	
		} catch (WorkflowServerException e) {
			logger.warning(Messages.SERVER_NOT_READY);
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
		
		List<String> checkNames=name;
		HashSet<String> names=WorkflowUtilities.splitList(checkNames, workflows.keySet());
		if(!checkNames.isEmpty()) {
			//unknown names
			UnknownNames err=Errors.unknownNames();
			err.getNames().addAll(checkNames);
			throw new UnknownNames_Exception(
					Messages.UNKNOWN_NAMES,
					err);
		
		}
		
		//-----------SELECT WORKFLOWS--------------------------
		
		List<WorkflowType> res=new ArrayList<WorkflowType>();
		for(String wfname : names) {			
			WorkflowType wf=workflows.get(wfname);
			if(processField==null) {	
				//processes not needed
				res.add(wf);
				continue;
			}
			
			//--------REBUILD WORKFLOW---------------------
			//add processes
			WorkflowType workflow=new WorkflowType();
			workflow.setName(wf.getName());
			workflow.getAction().addAll(wf.getAction());
			
			List<ProcessSummary> processes;
			try {
				processes = server.getProcesses(wfname);
			} catch (WorkflowServerException e) {
				logger.warning(Messages.SERVER_NOT_READY);
				throw new GenericFault_Exception(
						Messages.SERVICE_UNAVAILABLE,
						Errors.SERVICE_UNAVAILABLE,
						e);
			}
			if(processField.equals(ProcessFieldType.PROCESS_SUMMARY)) {
				//-----REBUILD PROCESSES------------
				for(ProcessSummary proc : processes) {
					ProcessSummary process=WorkflowUtilities.cloneProcessSummary(proc);
					WorkflowUtilities.modifyProcessSummaryFields(process,null);
					workflow.getProcessSummary().add(process);
				}
				
			} else {
				List<String> codes=workflow.getPCode();
				for(ProcessSummary proc : processes)
					codes.add(proc.getPCode());
			}
			
			res.add(workflow);
			
		}
		
		
		return res;
	}

	@Override
	public List<ProcessSummary> getProcessSummaries(Restrictions restrictions,
			Fields fields) throws GenericFault_Exception,
			UnknownCodes_Exception, UnknownNames_Exception,
			UnknownRoles_Exception {
		
		ProcessSummary process;
		HashSet<String> roles=null;
		HashSet<String> names=null;
		HashSet<String> codes=null;
		HashSet<ActorTypeHashable> actors=null;
		List<ProcessSummary> res=new ArrayList<ProcessSummary>();
		UnknownNames errNames=Errors.unknownNames();
		UnknownCodes errCodes=Errors.unknownCodes();
		UnknownRoles errRoles=Errors.unknownRoles();
		
		try {
			
			//apply restrictions on processes
			if(restrictions != null) {
				
				//---------CHECK FOR ERRORS IN RESTRICTIONS-------------
				//check and map roles (including actors' roles)
				List<String> checkRoles = restrictions.getRole();
				List<ActorType> checkActors = restrictions.getActor();
				HashSet<String> actorsRoles=null;
				if(!checkActors.isEmpty()) {
					actors=new HashSet<ActorTypeHashable>();
					actorsRoles=new HashSet<String>(); 
					for(ActorType actor : checkActors) {
						ActorTypeHashable a=new ActorTypeHashable();
						a.setName(actor.getName());
						a.setRole(actor.getRole());
						actors.add(a);
						checkRoles.add(actor.getRole());
						actorsRoles.add(actor.getRole());
					}
				}
				if(!checkRoles.isEmpty()) {
					roles=WorkflowUtilities.splitList(checkRoles, server.getRoles());
					if(!checkRoles.isEmpty()) {
						//unknown roles
						errRoles=Errors.unknownRoles();
						errRoles.getRoles().addAll(checkRoles);
						throw new UnknownRoles_Exception(
								Messages.UNKNOWN_ROLES, 
								errRoles);
					} else if (actors != null)
						roles.retainAll(actorsRoles);
				}
				
				//check and map names
				List<String> checkNames=restrictions.getWorkflowName();
				if(!checkNames.isEmpty()) {
					names=WorkflowUtilities.splitList(checkNames, server.getWorkflows().keySet());
					if(!checkNames.isEmpty()) {
						//unknown names
						errNames=Errors.unknownNames();
						errNames.getNames().addAll(checkNames);
						throw new UnknownNames_Exception(
								Messages.UNKNOWN_NAMES,
								errNames);
					}
				}
				
				//check and map codes
				List<String> checkCodes=restrictions.getPCode();
				if(!checkCodes.isEmpty()) {
					codes=WorkflowUtilities.splitList(checkCodes, server.getProcesses().keySet());
					if(!checkCodes.isEmpty()) {
						//unknown Codes
						errCodes=Errors.unknownCodes();
						errCodes.getCodes().addAll(checkCodes);
						throw new UnknownCodes_Exception(
								Messages.UNKNOWN_CODES,
								errCodes);
					}
				}
			
				//---------FILTER PROCESSES---------------------
				//filter processes by code and workflow name		
				if(codes != null) {
					//codes restrictions
					Map<String, ProcessSummary> processes=server.getProcesses();
					for(String PCode : codes) {
						process=processes.get(PCode);
						if(names == null || names.contains(process.getWorkflowName()))
							res.add(process);
					}
				} else if(names != null) {
					//names restrictions
					for(String name : names)
						res.addAll(server.getProcesses(name));
				} else {
					//no codes or names restrictions
					res.addAll(server.getProcesses().values());

				}

			} else {
				//no restrictions
				res.addAll(server.getProcesses().values());
			}
			
			//------REBUILD PROCESSES--------------------
			List<ProcessSummary> newres = new ArrayList<ProcessSummary>();
			if(roles == null) {
				//rebuild processes selecting requested fields
				for(ProcessSummary originalProc : res) {
					process=WorkflowUtilities.cloneProcessSummary(originalProc);
					WorkflowUtilities.modifyProcessSummaryFields(process, fields);
					//process rebuilt
					newres.add(process);
				}
			} else {
				//---------FILTER ACTIONS-----------------------------
				//rebuild processes filtering actions by actor and role
				for(ProcessSummary originalProc : res) {
					process=new ProcessSummary();
					//filter actions
					for(ActionStatusType status : originalProc.getActionStatus()) {						
						if(roles.contains(status.getAction().getRole()) &&
								(actors == null || 
								WorkflowUtilities.containsActor(actors, status.getActor())))
							process.getActionStatus().add(status);
					}
					//check for remaining actions
					if(!process.getActionStatus().isEmpty()) {
						//don't skip process
						process.setPCode(originalProc.getPCode());
						process.setStartTime(originalProc.getStartTime());
						process.setWorkflowName(originalProc.getWorkflowName());
						for(ActionStatusType status : originalProc.getActiveActionStatus())
							if(roles.contains(status.getAction().getRole()) &&
									(actors == null ||
									WorkflowUtilities.containsActor(actors, status.getActor())))
								process.getActiveActionStatus().add(status);
						
						for(ActionStatusType status : originalProc.getAvailableActionStatus())
							if(roles.contains(status.getAction().getRole()) &&
									(actors == null || actors.contains(status.getActor())))
								process.getAvailableActionStatus().add(status);
						for(ActionStatusType status : originalProc.getTerminatedActionStatus())
							if(roles.contains(status.getAction().getRole()) &&
									(actors == null || actors.contains(status.getActor())))
								process.getTerminatedActionStatus().add(status);
						
						WorkflowUtilities.modifyProcessSummaryFields(process, fields);
						//process rebuilt
						newres.add(process);
					}
				}
			}
			
			res=newres;
				
		} catch (WorkflowServerException e) {
			logger.warning(Messages.SERVER_NOT_READY);
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
		return res;
	}

}
