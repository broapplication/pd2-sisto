package it.polito.pd2.WF.sol6.client2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import it.polito.pd2.WF.sol6.client2.gen.ActionFieldType;
import it.polito.pd2.WF.sol6.client2.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.client2.gen.ActionsField;
import it.polito.pd2.WF.sol6.client2.gen.ActorType;
import it.polito.pd2.WF.sol6.client2.gen.GenericFault_Exception;
import it.polito.pd2.WF.sol6.client2.gen.GetProcessSummaries.Fields;
import it.polito.pd2.WF.sol6.client2.gen.GetProcessSummaries.Restrictions;
import it.polito.pd2.WF.sol6.client2.gen.ProcessPortType;
import it.polito.pd2.WF.sol6.client2.gen.ProcessService;
import it.polito.pd2.WF.sol6.client2.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.client2.gen.UnknownCodes_Exception;
import it.polito.pd2.WF.sol6.client2.gen.UnknownName;
import it.polito.pd2.WF.sol6.client2.gen.UnknownNames_Exception;
import it.polito.pd2.WF.sol6.client2.gen.UnknownRoles_Exception;
import it.polito.pd2.WF.sol6.client2.gen.WorkflowInfoPortType;
import it.polito.pd2.WF.sol6.client2.gen.WorkflowInfoService;
import it.polito.pd2.WF.sol6.client2.gen.WorkflowPortType;
import it.polito.pd2.WF.sol6.client2.gen.WorkflowService;
import it.polito.pd2.lab6.CommandReadException;
import it.polito.pd2.lab6.InputConsole;


public class ClientCore {
	private static final String REGEX_ALPHA="^[a-zA-Z]$|^[a-zA-Z][a-zA-Z ]*[a-zA-Z]$";
	private static final int OUT_OF_RANGE = -1;
	
	private static final String NOT_RUNNING = "Client is not running";
	private static final String UNEXPECTED_ERROR = "Unexpected error.";
	
	private static final Comparator<ProcessSummary> START_TIME_ORDER = 
			new Comparator<ProcessSummary>() {
				@Override
				public int compare(ProcessSummary p1,
						ProcessSummary p2) {
					return p1.getStartTime().compare(p2.getStartTime());
				}
			};
	
	private ReentrantReadWriteLock runningLock;
	
	private ActorType actor=null;
	
	private WorkflowPortType wf=null;
	private WorkflowInfoPortType wfInfo=null;
	private ProcessPortType proc=null;
	
	private String wfEndpoint=null;
	private String wfInfoEndpoint=null;
	private String procEndpoint=null;
	
	
	InputConsole console=new InputConsole();
	String clientID=null;
	AtomicInteger requestID=new AtomicInteger();
	
	public String getWfEndpoint() {
		runningLock.readLock().lock();
		String res;
		if(wf==null)
			res=null;
		else {
			BindingProvider bp = (BindingProvider) wf;
			res = (String) bp.getRequestContext().get(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		}
		runningLock.readLock().unlock();
		return res;
	}

	/**
	 * Setta l'endpoint da utilizzare per il servizio.
	 * La modifica non avra' effetto sino alla prossima chiamata di run o
	 * di conferma impostazioni.
	 * Per annullare la modifica, impostare <code>null</code>.
	 * 
	 * @param wfEndpoint
	 * l'URL da utilizzare come endpoint (tipo {@link String})
	 * @throws MalformedURLException
	 * non e' un URL valido
	 */
	public void setWfEndpoint(String wfEndpoint) throws MalformedURLException {
		if(wfEndpoint!=null)
			new URL(wfEndpoint);
		this.wfEndpoint = wfEndpoint;
	}

	public String getWfInfoEndpoint() {
		runningLock.readLock().lock();
		String res;
		if(wfInfo==null)
			res=null;
		else {
			BindingProvider bp = (BindingProvider) wfInfo;
			res = (String) bp.getRequestContext().get(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		}
		runningLock.readLock().unlock();
		return res;
	}

	/**
	 * Setta l'endpoint da utilizzare per il servizio.
	 * La modifica non avra' effetto sino alla prossima chiamata di run o
	 * di conferma impostazioni.
	 * Per annullare la modifica, impostare <code>null</code>.
	 * 
	 * @param wfInfoEndpoint
	 * l'URL da utilizzare come endpoint (tipo {@link String})
	 * @throws MalformedURLException
	 * non e' un URL valido
	 */
	public void setWfInfoEndpoint(String wfInfoEndpoint) throws MalformedURLException {
		if(wfInfoEndpoint!=null)
			new URL(wfInfoEndpoint);
		this.wfInfoEndpoint = wfInfoEndpoint;
	}

	public String getProcEndpoint() {
		runningLock.readLock().lock();
		String res;
		if(proc==null)
			res=null;
		else {
			BindingProvider bp = (BindingProvider) proc;
			res = (String) bp.getRequestContext().get(
				BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
		}
		runningLock.readLock().unlock();
		return res;
	}

	/**
	 * Setta l'endpoint da utilizzare per il servizio.
	 * La modifica non avra' effetto sino alla prossima chiamata di run o
	 * di conferma impostazioni.
	 * Per annullare la modifica, impostare <code>null</code>.
	 * 
	 * @param procEndpoint
	 * l'URL da utilizzare come endpoint (tipo {@link String})
	 * @throws MalformedURLException
	 * non e' un URL valido
	 */
	public void setProcEndpoint(String procEndpoint) throws MalformedURLException {
		if(procEndpoint!=null)
			new URL(procEndpoint);
		this.procEndpoint = procEndpoint;
	}
	
	

	
	public ClientCore(String actorName,String actorRole) 
			throws IllegalArgumentException {
		
		if(actorName==null)
			throw new IllegalArgumentException("actorName is null");
		if(!actorName.matches(REGEX_ALPHA))
			throw new IllegalArgumentException("actorName is incorrect");
		if(actorRole==null)
			throw new IllegalArgumentException("actorRole is null");
		if(!actorRole.matches(REGEX_ALPHA))
			throw new IllegalArgumentException("actorRole is incorrect");
		
		runningLock=new ReentrantReadWriteLock();
		actor=new ActorType();
		actor.setName(actorName);
		actor.setRole(actorRole);
		
		console.setSilent(true);
		
	}
	

	
	/**
	 * Imposta l'utilizzo degli endpoint precedentemente settati.
	 * 
	 * @throws ClientException
	 * il servizio non e' attivo, oppure errore interno
	 */
	public void updateEndpoints() throws ClientException {
		runningLock.readLock().lock();
		if(wf==null || wfInfo==null || proc==null) {
			runningLock.readLock().unlock();
			throw new ClientException(NOT_RUNNING);
		}
		
		BindingProvider bp;
		try {
			
			if(wfEndpoint != null) {
				bp = (BindingProvider) wf;
				bp.getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wfEndpoint);
				wfEndpoint=null;
			}
			
			if(wfInfoEndpoint != null) {
				bp = (BindingProvider) wfInfo;
				bp.getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wfInfoEndpoint);
				wfInfoEndpoint=null;
			}
			if(procEndpoint != null) {
				bp = (BindingProvider) proc;
				bp.getRequestContext().put(
						BindingProvider.ENDPOINT_ADDRESS_PROPERTY, procEndpoint);
				procEndpoint=null;
			}
		} catch (Exception e) {
			runningLock.readLock().unlock();
			throw new ClientException(e.getMessage());
		}
		runningLock.readLock().unlock();
	}
	
	private void upgradeLock(ReentrantReadWriteLock lock) {
		synchronized (this) {
			lock.readLock().unlock();
			lock.writeLock().lock();
		}		
	}
	
	private void downgradeLock(ReentrantReadWriteLock lock) {
		lock.readLock().lock();
		lock.writeLock().unlock();
	}
	 
		
	public void run() throws ClientException {
				
		runningLock.readLock().lock();					
		
		if(wf==null) {
			
			//------SETUP (exclusive lock)-----------------
			
			upgradeLock(runningLock);

			if(wf==null) {
				try {
					//first time running: initialize services
					WorkflowService wfService = new WorkflowService();
					WorkflowInfoService wfInfoService = new WorkflowInfoService();
					ProcessService procService = new ProcessService();
					
					wf=wfService.getWorkflowPort();
					wfInfo=wfInfoService.getWorkflowInfoPort();
					proc=procService.getProcessPort();	
				
				} catch (Exception e) {
					runningLock.writeLock().unlock();
					throw new ClientException(e.getMessage());
				}
			}
			
			downgradeLock(runningLock);			
		}

		//-----RUNNING (shared lock)-------------------								
		
		updateEndpoints();
				
		//if necessary, take client id
		if(clientID==null) {
			upgradeLock(runningLock);
			if(clientID==null)
				try {
					clientID=wf.takeClientID();
				} catch (GenericFault_Exception e) {
					runningLock.writeLock().unlock();
					throw new ClientException(e.getMessage());
				}
			downgradeLock(runningLock);
		}		

		System.out.println("Entering interactive mode..");
		try {
			int command=0;
			do {
				displayMenu();
				try{
					System.out.print("Select: ");
					command=getNextCommand();
					switch(command){
						case 0: 
							break;
						case 1: 
							createProcess();
							break;
						case 2: 
							takeInChargeAction();
							break;
						case 3: 
							terminateAction();
							break;
						case 4:
							changeRole();
							break;
					    default: 
					    	System.err.println("Bad command.");
					} 
				} catch (CommandReadException e) {
					System.err.println("Unable to read command.");
				}
			} while(command!=0);
		} catch (ClientException e) {
			//re-raise exception
			runningLock.readLock().unlock();
			System.out.println("Exiting interactive mode..");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			runningLock.readLock().unlock();
			System.out.println("Exiting interactive mode..");
			throw new ClientException(UNEXPECTED_ERROR);
		}
		
		System.out.println("Exiting interactive mode..");
	}


	private void createProcess() 
			throws ClientException, CommandReadException {
		if(wf==null || wfInfo==null)
			throw new ClientException(NOT_RUNNING);
		
		try {
			List<String> workflows = wfInfo.getWorkflowNames();
			Collections.sort(workflows);
			workflows.add("(back)");
					
			int selected = getSelection(workflows, "Select workflow: ");
			if(selected==workflows.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}
			
			ProcessSummary process = wf.newProcess(
					clientID, 
					requestID.getAndIncrement(), 
					workflows.get(selected));
			
			System.out.println("Process successfully created.");
			displayProcessSummary(process);
			System.out.println("Automatically instantiated actions:");
			if(process.getActions()==0) {
				System.out.println("no actions...");
			}
			for(ActionStatusType action : process.getActionStatus()) {
				displayActionStatus(action);				
			}
			
		} catch (CommandReadException e) {
			//re-raise exception
			throw e;
		} catch (GenericFault_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (UnknownName e) {
			throw new ClientException(e.getMessage());			
		} catch (Exception e) {
			throw new ClientException(UNEXPECTED_ERROR);
		}
		
	}

	private void displayProcessSummary(ProcessSummary process) {
		System.out.println("code:\t\t"		+ process.getPCode());
		System.out.println("workflow:\t"	+ process.getWorkflowName());
		System.out.println("start time:\t"	+ 
				process.getStartTime().toGregorianCalendar().getTime());
	}

	private void changeRole() 
			throws ClientException, CommandReadException {
		if(wf==null || wfInfo==null)
			throw new ClientException(NOT_RUNNING);

		try {
			List<String> roles = wfInfo.getRoles();
			Collections.sort(roles);
			roles.add("(back)");
			
			int selected = getSelection(roles, "Select role: ");
			if(selected==roles.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}
			
			String role=roles.get(selected);
			upgradeLock(runningLock);
			try {
				actor.setRole(role);
			} catch (Exception e) {
				downgradeLock(runningLock);
				throw new ClientException(UNEXPECTED_ERROR);
			}
			downgradeLock(runningLock);
			
			System.out.println("Role changed to " + role);
			
		} catch (CommandReadException e) {
			//re-raise exception
			throw e;
		} catch (ClientException e) {
			//re-raise exception
			throw e;
		} catch (GenericFault_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (Exception e) {
			throw new ClientException(UNEXPECTED_ERROR);
		}
		
	}

	private int getSelection(List<String> list, String prompt) 
			throws CommandReadException {
		int i=0;
		for(String el : list)
			System.out.println(Integer.toString(i++) + " " + el);
		
		
		System.out.print(prompt);
		int command = getNextCommand();
		if(command>=list.size() || command==OUT_OF_RANGE) {
			System.err.println("Bad choice.");
			return OUT_OF_RANGE;
		}
		
		return command;
	}

	private void terminateAction() 
			throws ClientException, CommandReadException {
		if(wf==null || wfInfo==null)
			throw new ClientException(NOT_RUNNING);
		
		try {
			Restrictions restrictions = new Restrictions();
			//restrictions.getRole().add(actor.getRole());
			Fields fields = new Fields();
			fields.setActions(ActionsField.LENGTH);
			//fields.setActiveActions(ActionsField.LIST);
			
			//get all processes
			List<ProcessSummary> processes = wfInfo.getProcessSummaries(restrictions, fields);
			Collections.sort(processes, START_TIME_ORDER);
			List<String> printableProcesses = getPrintableProcesses(processes);
			printableProcesses.add("(back)");
			int selected = getSelection(printableProcesses, "Select process: ");
			if(selected==printableProcesses.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}
			
			//get active actions (with embedded actions)
			String PCode = processes.get(selected).getPCode();
			restrictions.getActor().add(actor);
			restrictions.getRole().add(actor.getRole());
			restrictions.getPCode().add(PCode);
			fields.setActiveActions(ActionsField.LIST);
			fields.setActionFieldType(ActionFieldType.ACTION);
			processes = wfInfo.getProcessSummaries(restrictions, fields);
			
			//select action
			List<String> printableActions;
			List<ActionStatusType> actions = null;
			if(processes.isEmpty())
				printableActions=new ArrayList<String>();
			else {
				ProcessSummary process = processes.get(0);
				actions = process.getActiveActionStatus();
				printableActions = getPrintableActions(actions);
			}
			printableActions.add("(back)");
			selected = getSelection(printableActions, "Select action: ");
			if(selected==printableActions.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}

			ActionStatusType action = actions.get(selected);
			ArrayList<String> selectedNextActions=null;
			if( action.getAction().getWorkflow()==null) {
				//simple action
				//select next actions
				List<String> nextAction = action.getAction().getNextAction();
				Collections.sort(nextAction);
				nextAction.add(0,"(done)");
				
				selectedNextActions = new ArrayList<String>();
				do {
					if(!selectedNextActions.isEmpty())
						System.out.println("Current selection: " + selectedNextActions.toString());
					
					selected = getSelection(nextAction, "Select next action: ");
					if(selected==OUT_OF_RANGE) {
						System.out.println("Operation aborted.");
						return;
					}
					if(selected>0)
						selectedNextActions.add(nextAction.remove(selected));
				} while(selected>0);
			}
			
			//terminate action
			
			Holder<XMLGregorianCalendar> terminationTime=new Holder<XMLGregorianCalendar>();
			Holder<ProcessSummary> nextProcess=new Holder<ProcessSummary>();
			Holder<List<ActionStatusType>> nextActionStatus=new Holder<List<ActionStatusType>>();
			proc.terminateAction(
					clientID, 
					requestID.getAndIncrement(),
					PCode, 
					action.getACode(),
					selectedNextActions,
					terminationTime, nextProcess, nextActionStatus);
			
			System.out.println("Termination time: " + 
					terminationTime.value.toGregorianCalendar().getTime().toString());
			
			if(nextProcess.value != null) {
				displayProcessSummary(nextProcess.value);
				for(ActionStatusType act : nextProcess.value.getActionStatus()) {
					displayActionStatus(act);				
				}
			}
			if(nextActionStatus.value != null)
				for(ActionStatusType status : nextActionStatus.value)
					displayActionStatus(status);				
			
		} catch (CommandReadException e) {
			//re-raise exception
			throw e;
		} catch (GenericFault_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (UnknownCodes_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (UnknownNames_Exception e) {
			throw new ClientException(e.getMessage());	
		} catch (UnknownRoles_Exception e) {
			throw new ClientException(e.getMessage());		
		} catch (Exception e) {
			throw new ClientException(UNEXPECTED_ERROR);
		}
	}

	private void takeInChargeAction() 
			throws ClientException, CommandReadException {
		if(wf==null || wfInfo==null)
			throw new ClientException(NOT_RUNNING);
		
		try {
			Restrictions restrictions = new Restrictions();
			//restrictions.getRole().add(actor.getRole());
			Fields fields = new Fields();
			fields.setActions(ActionsField.LENGTH);
			//fields.setAvailableActions(ActionsField.LIST);
			fields.setAvailableActions(ActionsField.LENGTH);
			
			//get all processes
			List<ProcessSummary> processes = wfInfo.getProcessSummaries(restrictions, fields);
			Collections.sort(processes, START_TIME_ORDER);
			List<String> printableProcesses = getPrintableProcesses(processes);
			printableProcesses.add("(back)");
			
			int selected = getSelection(printableProcesses, "Select process: ");
			if(selected==printableProcesses.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}
			
			//get available actions
			String PCode = processes.get(selected).getPCode();
			
			restrictions.getRole().add(actor.getRole());
			restrictions.getPCode().add(PCode);
			fields.setAvailableActions(ActionsField.LIST);
			processes = wfInfo.getProcessSummaries(restrictions, fields);
			
			//select action
			List<String> printableActions;
			List<ActionStatusType> actions = null;
			if(processes.isEmpty())
				printableActions=new ArrayList<String>();
			else {
				ProcessSummary process = processes.get(0);
				actions = process.getAvailableActionStatus();
				printableActions = getPrintableActions(actions);
			}
			printableActions.add("(back)");
			selected = getSelection(printableActions, "Select action: ");
			if(selected==printableActions.size()-1 || selected==OUT_OF_RANGE) {
				System.out.println("Operation aborted.");
				return;
			}
			
			//take in charge action
			ActionStatusType action = proc.takeInCharge(
					clientID, 
					requestID.getAndIncrement(),
					PCode, 
					actions.get(selected).getACode(),
					actor);
			
			System.out.println("Action taken in charge.");
			displayActionStatus(action);				
			
		} catch (CommandReadException e) {
			//re-raise exception
			throw e;
		} catch (GenericFault_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (UnknownCodes_Exception e) {
			throw new ClientException(e.getMessage());
		} catch (UnknownNames_Exception e) {
			throw new ClientException(e.getMessage());	
		} catch (UnknownRoles_Exception e) {
			throw new ClientException(e.getMessage());		
		} catch (Exception e) {
			throw new ClientException(UNEXPECTED_ERROR);
		}
	}
	
	
	private int getNextCommand() {
			try {
				return console.getNextCommand();
			} catch (Exception e) {
				return OUT_OF_RANGE;
			}
	}

	private void displayActionStatus(ActionStatusType action) {
		System.out.println("code:\t\t"		+ action.getACode());
		if(action.getAction()!=null) {
			System.out.println("name:\t\t"		+ action.getAction().getName());
			System.out.println("role:\t\t"		+ action.getAction().getRole());
		} else {
			System.out.println("name:\t\t"		+ action.getName());
		}
		ActorType actor=action.getActor();
		XMLGregorianCalendar time = action.getTerminationTime();

		if(actor==null) {
			System.out.println("state:\t\tavailable");
		}
		else if(time==null) {
			System.out.println("state:\t\tactive");
			System.out.println("actor:\t\t"		+ action.getActor().getName());
		} else {
			System.out.println("state:\t\tterminated");
			System.out.println("term. time:\t"	+ 
					action.getTerminationTime().toGregorianCalendar().getTime());
		}			
	}

	private List<String> getPrintableActions(List<ActionStatusType> actions) {
		ArrayList<String> res = new ArrayList<String>();
		for(ActionStatusType action : actions)
			res.add(action.getName() + "(" + action.getACode() + ")");
		return res;
	}

	private List<String> getPrintableProcesses(List<ProcessSummary> processes) {
		ArrayList<String> res = new ArrayList<String>();
		for(ProcessSummary process : processes)
			res.add(process.getWorkflowName() + 
					"(" + process.getPCode() + ") - " + 
					process.getStartTime().toGregorianCalendar().getTime().toString());
		return res;
	}

	private void  displayMenu(){
		System.out.println("# "+actor.getName()+" - "+actor.getRole()+" #");
		System.out.println("0 quit");
		System.out.println("1 new process");
		System.out.println("2 take in charge action");
		System.out.println("3 terminate action");
		System.out.println("4 change role");
	}
	
}
