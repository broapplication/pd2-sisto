package it.polito.pd2.WF.sol6.service;

import java.util.logging.Logger;

import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Errors;
import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Messages;
import it.polito.pd2.WF.sol6.service.gen.GenericFault_Exception;
import it.polito.pd2.WF.sol6.service.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.service.gen.UnknownName;
import it.polito.pd2.WF.sol6.service.gen.WorkflowPortType;
import javax.jws.WebService;

@WebService(
		name="WorkflowPortType", 
		endpointInterface="it.polito.pd2.WF.sol6.service.gen.WorkflowPortType", 
		wsdlLocation="META-INF/wsService.wsdl",
		targetNamespace="http://pad.polito.it/Workflow", 
		serviceName="WorkflowService", portName="WorkflowPort")
public class WorkflowServiceImpl implements WorkflowPortType {

	private WorkflowServer server;
	private static Logger logger = Logger.getLogger(WorkflowServiceImpl.class.getName());
	
	public WorkflowServiceImpl() {
		server=WorkflowServer.getInstance();
	}
	
	@Override
	public String takeClientID() throws GenericFault_Exception {
		try {
			return server.takeClientID();
		} catch (WorkflowServerException e) {
			logger.warning(e.getMessage());
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
	}
	
	@Override
	public ProcessSummary newProcess(String clientID, int requestID,
			String workflowName) throws GenericFault_Exception, UnknownName {
		if(clientID==null)
			throw new GenericFault_Exception(
					Messages.REQUEST_NOT_WELL_FORMED, 
					Errors.REQUEST_NOT_WELL_FORMED);
		
		try {
			if(!server.getWorkflows().containsKey(workflowName))
				throw new UnknownName(
						Messages.UNKNOWN_NAME, 
						Errors.UNKNOWN_NAME);
			
			if(server.getClientState(clientID) == null) {
				logger.warning(Messages.UNKNOWN_CLIENT_ID);
				throw new GenericFault_Exception(
						Messages.UNKNOWN_CLIENT_ID, 
						Errors.UNKNOWN_CLIENT_ID);
			}
				
			
			server.serveRequest(clientID, requestID);
			ProcessSummary process = WorkflowUtilities.cloneProcessSummary(
					server.createProcess(workflowName));	
			WorkflowUtilities.modifyProcessSummaryFields(process,null);
			return process;
			
		} catch (WorkflowServerException e) {
			logger.warning(e.getMessage());
			throw new GenericFault_Exception(
					Messages.SERVICE_UNAVAILABLE,
					Errors.SERVICE_UNAVAILABLE,
					e);
		}
	}
	
	

}
