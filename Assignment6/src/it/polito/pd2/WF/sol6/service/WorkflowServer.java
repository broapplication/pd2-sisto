package it.polito.pd2.WF.sol6.service;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.WorkflowReader;
import it.polito.pd2.WF.Random.ProcessAction;
import it.polito.pd2.WF.Random.SimpleAction;
import it.polito.pd2.WF.sol6.service.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.service.gen.ActionType;
import it.polito.pd2.WF.sol6.service.gen.ActorType;
import it.polito.pd2.WF.sol6.service.gen.ProcessSummary;
import it.polito.pd2.WF.sol6.service.gen.TerminateActionResponse;
import it.polito.pd2.WF.sol6.service.gen.WorkflowType;
import it.polito.pd2.WF.sol6.service.WorkflowUtilities.Messages;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Endpoint;

public class WorkflowServer {
	private static final int THREADS = 20;
	private boolean ready = false;
	private ReentrantReadWriteLock readyLock;

	private static Logger logger = Logger.getLogger(WorkflowServer.class.getName());


	private Set<String> roles;
	private HashMap<String, WorkflowType> workflows;
	private ConcurrentHashMap<String, ProcessSummary> processes;
	private ConcurrentHashMap<String, CopyOnWriteArrayList<ProcessSummary>> wfProcesses;
	private ConcurrentHashMap<String, ClientState> clients;

	private AtomicInteger PCodesCounter;
	private Map<String, AtomicInteger> ACodesCounters;


	private static class WorkflowServerHolder {
		private final static WorkflowServer INSTANCE=new WorkflowServer();
	}

	public static WorkflowServer getInstance() {
		return WorkflowServerHolder.INSTANCE;
	}

	private WorkflowServer() {
		readyLock=new ReentrantReadWriteLock();
		roles=new TreeSet<String>();
		workflows=new HashMap<String, WorkflowType>();
		processes=new ConcurrentHashMap<String, ProcessSummary>();
		wfProcesses=new ConcurrentHashMap<String, CopyOnWriteArrayList<ProcessSummary>>();

		WorkflowMonitorFactory factory=WorkflowMonitorFactory.newInstance();
		WorkflowMonitor monitor=factory.newWorkflowMonitor();

		PCodesCounter=new AtomicInteger();
		ACodesCounters=new ConcurrentHashMap<String, AtomicInteger>();
		initWorkflows(monitor.getWorkflows());
		clients=new ConcurrentHashMap<String, ClientState>();
		setReady(true);
	}

	/**
	 * Da usare per portare il server in manutenzione o in funzione.
	 * Attende l'unlock da parte di tutti i reader.
	 * 
	 * In manutenzione, tutte le richieste esterne sono ignorate e il
	 * server rispondera'  con una eccezione {@link WorkflowServerException}.
	 * 
	 * @param value
	 * <code>true</code> porta il server in funzione,
	 * <code>false</code> in manutenzione
	 */
	public void setReady(boolean value) {
		readyLock.writeLock().lock();
		ready=value;
		readyLock.writeLock().unlock();
	}

	public Set<String> getRoles() throws WorkflowServerException {
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}
		TreeSet<String> res;
		if(roles==null)
			res=new TreeSet<String>();
		else
			res=new TreeSet<String>(roles);
		readyLock.readLock().unlock();
		return res;	
	}

	public Map<String, WorkflowType> getWorkflows() throws WorkflowServerException {
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

		HashMap<String, WorkflowType> res;
		if(workflows==null)
			res=new HashMap<String, WorkflowType>();
		else
			res=new HashMap<String, WorkflowType>(workflows);
		readyLock.readLock().unlock();
		return res;
	}

	public Map<String, ProcessSummary> getProcesses() throws WorkflowServerException {
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}
		HashMap<String, ProcessSummary> res;
		if(processes==null)
			res=new HashMap<String, ProcessSummary>();
		else
			res=new HashMap<String, ProcessSummary>(processes);

		readyLock.readLock().unlock();
		return res;
	}


	public List<ProcessSummary> getProcesses(String workflowName) 
			throws WorkflowServerException, IllegalArgumentException {

		if(workflowName==null)
			throw new IllegalArgumentException("workflowName is null");
		if(workflowName.isEmpty())
			throw new IllegalArgumentException("workflowName is empty");

		readyLock.readLock().lock();

		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		} 

		CopyOnWriteArrayList<ProcessSummary> list = wfProcesses.get(workflowName);

		if(list==null) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNKNOWN_NAME);
		}

		readyLock.readLock().unlock();
		return new ArrayList<ProcessSummary>(list);
	}

	/**
	 * Alloca un nuovo client id e inizializza lo stato del client.
	 * Se il server e' in manutenzione, la richiesta e' ignorata e viene
	 * sollevata un'eccezione.
	 * 
	 * @return
	 * il nuovo client id (tipo {@link String})
	 * @throws WorkflowServerException
	 * il server e' in manutenzione
	 */
	public String takeClientID() throws WorkflowServerException {
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

		String res=null;
		try {
			ClientState client=new ClientState();			
			do {
				res=UUID.randomUUID().toString();
			} while (clients.putIfAbsent(res, client) != null);

		}catch (Exception e) {
			logger.severe(Messages.UNEXPECTED_SERVER_ERROR + ": " + e.getMessage());
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNEXPECTED_SERVER_ERROR);	
		} 

		readyLock.readLock().unlock();
		return res;
	}

	public ClientState getClientState(String clientID)  
			throws WorkflowServerException, IllegalArgumentException {

		if(clientID==null)
			throw new IllegalArgumentException("clientID is null");
		if(clientID.isEmpty())
			throw new IllegalArgumentException("clientID is empty");

		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

		ClientState res=clients.get(clientID);
		if(res==null) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNKNOWN_CLIENT_ID);
		}

		readyLock.readLock().unlock();
		return res;
	}

	/**
	 * Se la richiesta e' stata gia' servita in precedenza,
	 * solleva un'eccezione, altrimenti la registra.
	 * 
	 * Il metodo e' thread safe.
	 * 
	 * @param clientID
	 * @param requestID
	 * @throws WorkflowServerException
	 * @throws IllegalArgumentException
	 */
	public void serveRequest(String clientID, int requestID) 
			throws WorkflowServerException, IllegalArgumentException {

		ClientState client=getClientState(clientID);

		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

		if(!client.addRequest(requestID)) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.REQUEST_ALREADY_SERVED);
		}
		readyLock.readLock().unlock();
	}

	private static XMLGregorianCalendar getXMLtimeNow() throws WorkflowServerException {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(
					(GregorianCalendar) GregorianCalendar.getInstance());
		} catch (Exception e) {
			logger.severe(Messages.UNEXPECTED_SERVER_ERROR);
			throw new WorkflowServerException(e.getMessage());
		}
	}

	/**
	 * Crea una nuova istanza  di un'azione, con valori di default.
	 * L'istanza potra' essere aggiunta allo stato di un processo.
	 * 
	 * Solleva un'eccezione se si riscontra un errore nei parametri di
	 * ingresso. Gli errori sono registrati nel log.
	 * 
	 * @param action
	 * @param ACode
	 * @return
	 * @throws WorkflowServerException
	 */
	private ActionStatusType createActionInstance(ActionType action, String ACode) 
			throws WorkflowServerException {
		//log internal errors
		if(action==null) {
			logger.severe("action is null");
			throw new WorkflowServerException("action is null");
		}
		if(ACode==null) {
			logger.severe("ACode is null");
			throw new WorkflowServerException("ACode is null");
		}
		if(ACode.isEmpty()) {
			logger.severe("ACode is empty");
			throw new WorkflowServerException("ACode is empty");
		}
		
		try {
			ActionStatusType res=new ActionStatusType();
			res.setACode(ACode);
			res.setAction(action);
			res.setName(action.getName());
			res.setActor(null);
			res.setTerminationTime(null);
			return res;
		} catch (Exception e) {
			logger.severe(Messages.UNEXPECTED_SERVER_ERROR);
			throw new WorkflowServerException(Messages.UNEXPECTED_SERVER_ERROR);
		}
		
	}

	/**
	 * Crea un nuovo processo e lo aggancia alle liste.<br />
	 * Solleva un'eccezione se il server e' in manutenzione, se l'argomento
	 * non e' valido oppure se non esiste il workflow passato per argomento. <br />
	 * <br />
	 * Il metodo e' thread safe, non crea conflitti nei codici associati
	 * ai processi e alle istanze delle azioni.
	 * 
	 * @param workflowName
	 * tipo {@link String}
	 * 
	 * @return
	 * il processo creato (tipo {@link ProcessSummary})
	 * 
	 * @throws WorkflowServerException
	 * errore nella creazione del processo, oppure il server e' in manutenzione
	 * @throws IllegalArgumentException
	 * parametro in ingresso non valido
	 */
	public ProcessSummary createProcess(String workflowName) 
			throws WorkflowServerException, IllegalArgumentException {
		
		if(workflowName==null)
			throw new IllegalArgumentException("workflowName is null");
		if(workflowName.isEmpty())
			throw new IllegalArgumentException("workflowName is empty");

		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

		WorkflowType workflow = workflows.get(workflowName);
		CopyOnWriteArrayList<ProcessSummary> workflowProc = wfProcesses.get(workflowName);
		if(workflow == null || workflowProc == null) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNKNOWN_NAME);
		}
		
		
		try {
			ProcessSummary process=new ProcessSummary();
			String PCode="P"+Integer.toString(PCodesCounter.getAndIncrement());
			process.setPCode(PCode);
			process.setStartTime(getXMLtimeNow());
			process.setWorkflowName(workflowName);
			process.setActiveActions(0);
			process.setTerminatedActions(0);
	
			int count=0;
			List<ActionStatusType> status=process.getActionStatus();
			for(ActionType action : workflow.getAction())
				if(action.isAutomaticallyInstantiated()) {
					status.add(createActionInstance(action,
							PCode + "A" + Integer.toString(count)));
					count++;
				}
			process.setActions(count);
			process.setAvailableActions(count);
			process.getAvailableActionStatus().addAll(status);
	
			//attach process
			ACodesCounters.put(PCode, new AtomicInteger(count));
			processes.put(PCode, process);
			workflowProc.add(process);
			
			readyLock.readLock().unlock();
			return process;
		} catch (Exception e) {
			logger.severe(Messages.UNEXPECTED_SERVER_ERROR);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNEXPECTED_SERVER_ERROR);
		}
	}

	/**
	 * Cambia lo stato dell'istanza dell'azione da attivo a terminato.
	 * Istanzia le successive azioni passate come argomento (se azione semplice),
	 * oppure il nuovo processo (se azione di processo). Ritorna Le nuove azioni 
	 * o il nuovo processo istanziati in una classe {@link TerminateActionResponse}.<br>
	 * Solleva un'eccezione se il server e' in manutenzione oppure se gli argomenti
	 * non sono validi.<br>
	 * <br>
	 * Il metodo e' thread safe. Le modifiche al processo sono bloccate in mutua 
	 * esclusione fino alla terminazione. Al termine della modifica, il processo
	 * sara' sostituito nella coda dei processi. Grazie al double-locking, ogni thread
	 * modifichera' solo l'ultima versione del processo.
	 * @param PCode
	 * il codice del processo (tipo {@link String})
	 * @param status
	 * l'istanza dell'azione da terminare (tipo {@link ActionStatusType})
	 * @param nextAction
	 * la lista delle azioni successive da istanziare (elementi di tipo {@link String})
	 * @throws WorkflowServerException
	 * errore nell'aggiornamento dei dati, oppure il server e' in manutenzione
	 * @throws IllegalArgumentException
	 * parametri non validi
	 */
	public TerminateActionResponse terminateAction(String PCode,
			ActionStatusType status, List<String> nextAction) 
					throws WorkflowServerException, IllegalArgumentException {
		TerminateActionResponse res;
		
		if(PCode == null)
			throw new IllegalArgumentException("PCode is null");
		if(PCode.isEmpty())
			throw new IllegalArgumentException("PCode is empty");
		if(status == null)
			throw new IllegalArgumentException("status is null");
		
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}

			
		try {
			ProcessSummary process = processes.get(PCode);
			if(process == null) {
				throw new WorkflowServerException(Messages.UNKNOWN_CODE);
			}
			CopyOnWriteArrayList<ProcessSummary> workflowProc = 
					wfProcesses.get(process.getWorkflowName());

			//DOUBLE LOCKING ON PROCESS
			
			//exclusive lock on process to be replaced
			synchronized (process) {
				
				//get last instance of process (could be replaced by another thread)
				process = processes.get(PCode);
				if(process == null) {
					throw new WorkflowServerException(Messages.UNKNOWN_CODE);
				}

				synchronized (process) {
					res=new TerminateActionResponse();
					
					//create new process (duplicate old one)
					ProcessSummary newProcess = WorkflowUtilities.cloneProcessSummary(process);
					
					//change action status
					if(!newProcess.getActiveActionStatus().remove(status)) 
						throw new WorkflowServerException(Messages.UNAVAILABLE_ACTION);
					newProcess.setActiveActions(newProcess.getAvailableActions()-1);
		
					ActionStatusType newStatus = WorkflowUtilities.cloneActionStatus(status);
					XMLGregorianCalendar terminationTime = getXMLtimeNow();
					newStatus.setTerminationTime(terminationTime);
					res.setTerminationTime(terminationTime);
		
					newProcess.getTerminatedActionStatus().add(newStatus);
					newProcess.setTerminatedActions(newProcess.getTerminatedActions()+1);
					replaceElement(newProcess.getActionStatus(), status, newStatus);
					
					//check action type (simple/process)
					String workflow = newStatus.getAction().getWorkflow();
					if(workflow != null && !workflow.isEmpty()) {
						//process action - instantiate next process
						if(nextAction != null && !nextAction.isEmpty())
							throw new WorkflowServerException(Messages.ERROR_NEXT_ACTIONS);
						res.setNextProcess(createProcess(workflow));
						
					} else if(nextAction != null) {
						//simple action - instantiate next actions
						ArrayList<ActionStatusType> list = new ArrayList<ActionStatusType>();
						for(ActionType action : workflows.get(newProcess.getWorkflowName()).getAction()) {
							if(!nextAction.contains(action.getName()))
								continue;
			
							list.add(createActionInstance(action,PCode + "A" + 
									Integer.toString(ACodesCounters.get(PCode).getAndIncrement())));	
						}
						newProcess.getActionStatus().addAll(list);
						newProcess.setActions(newProcess.getActions()+list.size());
						newProcess.getAvailableActionStatus().addAll(list);
						newProcess.setAvailableActions(newProcess.getAvailableActions()+list.size());
						res.getNextActionStatus().addAll(list);
					}
					
					//replace old process
					processes.replace(PCode, newProcess);
					replaceElement(workflowProc, process, newProcess);
					
					
				}	//first unlock
			}	//second unlock
			
		}catch (WorkflowServerException e) {
			//unlock and re-raise exception
			readyLock.readLock().unlock();
			throw e;
		} catch (Exception e) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNEXPECTED_SERVER_ERROR);
		}
		readyLock.readLock().unlock();
		return res;
	}

	/**
	 * Assegna l'istanza di un'azione, se non e' stata ancora presa in carico.
	 * Crea una nuova istanza e la sostituisce alla vecchia.
	 * L'istanza creata viene restituita al termine della funzione.
	 * Solleva un'eccezione se il server e' in manutenzione, se gli argomenti
	 * non sono validi oppure se e' non e' possibile assegnarla.<br>
	 * <br>
	 * Il metodo e' thread safe. Le modifiche al processo sono bloccate in mutua 
	 * esclusione fino alla terminazione. Al termine della modifica, il processo
	 * sara' sostituito nella coda dei processi. Grazie al double-locking, ogni thread
	 * modifichera' solo l'ultima versione del processo.
	 * @param PCode
	 * il codice del processo (tipo {@link String})
	 * @param status
	 * l'istanza dell'azione da prendere in carico (tipo {@link ActionStatusType})
	 * @param actor
	 * a chi assegnare l'istanza (tipo {@link ActorType})
	 * @return
	 * la nuova istanza dell'azione (tipo {@link ActionStatusType})
	 * @throws WorkflowServerException
	 * errore nell'aggiornamento dei dati, oppure il server e' in manutenzione
	 * @throws IllegalArgumentException
	 * parametri non validi
	 */
	public ActionStatusType takeInChargeAction(String PCode, 
			ActionStatusType status, ActorType actor) 
					throws WorkflowServerException, IllegalArgumentException {
		ActionStatusType res;
		
		if(PCode == null)
			throw new IllegalArgumentException("PCode is null");
		if(PCode.isEmpty())
			throw new IllegalArgumentException("PCode is empty");
		if(status == null)
			throw new IllegalArgumentException("status is null");
		if(actor == null)
			throw new IllegalArgumentException("actor is null");

		if(!checkActor(actor))
			throw new WorkflowServerException(Messages.INVALID_ACTOR);

		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}


		try {

			ProcessSummary process = processes.get(PCode);
			if(process == null) {
				throw new WorkflowServerException(Messages.UNKNOWN_CODE);
			}
			CopyOnWriteArrayList<ProcessSummary> workflowProc = 
					wfProcesses.get(process.getWorkflowName());

			//DOUBLE LOCKING ON PROCESS

			//exclusive lock on process to be replaced
			synchronized (process) {

				//get last instance of process (could be replaced by another thread)
				process = processes.get(PCode);
				if(process == null) {
					throw new WorkflowServerException(Messages.UNKNOWN_CODE);
				}

				synchronized (process) {

					//create new process (duplicate old one)
					ProcessSummary newProcess = WorkflowUtilities.cloneProcessSummary(process);

					//change action status
					
					if(!newProcess.getAvailableActionStatus().remove(status))
						throw new WorkflowServerException(Messages.UNAVAILABLE_ACTION);
					newProcess.setAvailableActions(newProcess.getAvailableActions()-1);

					res = WorkflowUtilities.cloneActionStatus(status);
					res.setActor(actor);

					newProcess.getActiveActionStatus().add(res);
					newProcess.setActiveActions(newProcess.getActiveActions()+1);
					replaceElement(newProcess.getActionStatus(), status, res);		

					//replace old process
					processes.replace(PCode, newProcess);
					replaceElement(workflowProc, process, newProcess);

				}	//first unlock
			}	//second unlock

		}catch (WorkflowServerException e) {
			//unlock and re-raise exception
			readyLock.readLock().unlock();
			throw e;
		} catch (Exception e) {
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.UNEXPECTED_SERVER_ERROR);
		}
		readyLock.readLock().unlock();
		return res;
	}

	private static <E> void replaceElement(List<E> list, E oldEl, E newEl) {
		int index = list.indexOf(oldEl);
		list.set(index, newEl);
	}

	private void initWorkflows(Set<WorkflowReader> wfs) {
		if(wfs==null)
			return;

		for(WorkflowReader wf: wfs) {
			String name=wf.getName();
			WorkflowType workflow=new WorkflowType();
			workflow.setName(name);

			List<ActionType> actions=workflow.getAction();
			for(ActionReader act : wf.getActions()) {
				String role=act.getRole();
				roles.add(role);

				ActionType action=new ActionType();
				action.setName(act.getName());
				action.setRole(role);
				action.setAutomaticallyInstantiated(act.isAutomaticallyInstantiated());

				if(act instanceof SimpleAction) {
					List<String> next=action.getNextAction();
					for(ActionReader nact : ((SimpleAction)act).getPossibleNextActions())
						next.add(nact.getName());
				} else
					action.setWorkflow(((ProcessAction)act).getActionWorkflow().getName());
				actions.add(action);
			}
			wfProcesses.put(name, new CopyOnWriteArrayList<ProcessSummary>());
			workflows.put(name, workflow);
		}

		logger.fine(Messages.workflowsOk(workflows.keySet().toString()));	
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(Messages.settingUp(System.getProperty("WORKFLOW_SERVICE")));
			Endpoint wfEndpoint=Endpoint.create(new WorkflowServiceImpl());
			wfEndpoint.setExecutor(Executors.newFixedThreadPool(THREADS));
			wfEndpoint.publish(System.getProperty("WORKFLOW_SERVICE"));

			System.out.println(Messages.settingUp(System.getProperty("WORKFLOW_INFO_SERVICE")));
			Endpoint wfInfoEndpoint=Endpoint.create(new WorkflowInfoServiceImpl());
			wfInfoEndpoint.setExecutor(Executors.newFixedThreadPool(THREADS));
			wfInfoEndpoint.publish(System.getProperty("WORKFLOW_INFO_SERVICE"));

			System.out.println(Messages.settingUp(System.getProperty("PROCESS_SERVICE")));
			Endpoint procEndpoint=Endpoint.create(new ProcessServiceImpl());
			procEndpoint.setExecutor(Executors.newFixedThreadPool(THREADS));
			procEndpoint.publish(System.getProperty("PROCESS_SERVICE"));
		} catch (Exception e) {
			logger.severe(Messages.SETTING_UP_ERR);
			System.exit(1);
		}

		logger.fine(Messages.SETTING_UP_OK);
	}
	
	/**
	 * Controlla che l'argomento sia un possibile attore.<br />
	 * Non e' assicurato che l'attore sia assegnato ad azioni
	 * di un qualche processo, ma solo che il ruolo sia tra quelli
	 * disponibili e che il nome non sia scorretto.<br />
	 * Solleva un'eccezione se il server e' in manutenzione, oppure
	 * se viene passato un valore null.
	 * 
	 * @param actor
	 * tipo {@link ActorType}
	 * @return
	 * <code>true</code> se l'argomento e' un possibile attore<br>
	 * <code>false</code> se il nome o il ruole sono scorretti
	 * @throws WorkflowServerException
	 * il server e' in manutenzione
	 * @throws IllegalArgumentException
	 * parametro null
	 */
	public boolean checkActor(ActorType actor) 
			throws WorkflowServerException, IllegalArgumentException {
		if(actor==null)
			throw new IllegalArgumentException("actor is null");
		
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}
		try {
			String name = actor.getName();
			String role = actor.getRole();
			boolean res=(
					name!=null && role!=null && 
					name.matches(WorkflowUtilities.REGEX_ALPHA) &&
					role.matches(WorkflowUtilities.REGEX_ALPHA) &&
					roles.contains(role));
			readyLock.readLock().unlock();
			return res;
		} catch (Exception e) {
			readyLock.readLock().unlock();
			return false;
		}
	}
	
	/**
	 * Controlla che il processo sia presente nel server.<br />
	 * Il controllo e' fatto sull'oggetto, non sui campi, percio' un
	 * summary diverso, ma associato allo stesso processo, restituira'
	 * un fallimento. Il processo passato come argomento deve essere
	 * quello effettivamente restituito dal server in una precedente
	 * richiesta.<br />
	 * Solleva un'eccezione se il server e' in manutenzione, oppure
	 * se viene passato un valore null.
	 * 
	 * @param process
	 * tipo {@link ProcessSummary}
	 * @return
	 * <code>true</code> se presente, altrimenti <code>false</code>.
	 * @throws WorkflowServerException
	 * il server e' in manutenzione
	 * @throws IllegalArgumentException
	 * parametro null
	 */
	public boolean checkProcess(ProcessSummary process) 
			throws WorkflowServerException, IllegalArgumentException {
		if(process==null)
			throw new IllegalArgumentException("process is null");
		
		readyLock.readLock().lock();
		if(!ready) {
			logger.warning(Messages.REQUEST_WHEN_NOT_READY);
			readyLock.readLock().unlock();
			throw new WorkflowServerException(Messages.SERVER_NOT_READY);
		}
		
		try {
			boolean res = process.equals(processes.get(process.getPCode()));
			readyLock.readLock().unlock();
			return res;
		} catch (Exception e) {
			readyLock.readLock().unlock();
			return false;
		}		
	}

}
