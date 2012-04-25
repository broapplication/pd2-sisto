package it.polito.pd2.WF.sol4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.Actor;
import it.polito.pd2.WF.ProcessActionReader;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.SimpleActionReader;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowReader;

/**
 * <p>Java class for workflowType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workflowType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="simpleAction" type="{http://www.example.org/wfInfo}simpleActionType"/>
 *           &lt;element name="processAction" type="{http://www.example.org/wfInfo}processActionType"/>
 *         &lt;/choice>
 *         &lt;element name="process" type="{http://www.example.org/wfInfo}processType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.example.org/wfInfo}idType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workflowType", propOrder = {
		"actionList",
		"processList"
})
public class WorkflowImpl implements 
WorkflowReader, Unmarshallable {

	private static final String ERROR_ROLE = "error different role";

	@XmlTransient
	private Map<String, ActionImpl> actions;
	@XmlTransient
	private Map<String, List<SimpleActionImpl>> actionRefs;
	@XmlTransient
	private LinkedHashSet<ProcessReader> processes;
	@XmlTransient
	private WorkflowMonitorImpl monitor;

	@XmlAttribute(name = "name", required = true)
	protected String name;


	public WorkflowImpl() {
		actionRefs=new HashMap<String, List<SimpleActionImpl>>();
		actions=new LinkedHashMap<String, ActionImpl>();
		processes=new LinkedHashSet<ProcessReader>();
	}

	public WorkflowImpl(WorkflowReader workflow, WorkflowMonitorImpl monitor) {
		this();
		this.monitor=monitor;

		setName(workflow.getName());

		//add actions
		for(ActionReader action : workflow.getActions())
			if(action instanceof SimpleActionReader)
				actions.put(action.getName(), new SimpleActionImpl((SimpleActionReader) action,this));
			else
				actions.put(action.getName(), new ProcessActionImpl((ProcessActionReader)action,this));
		
		linkActions();

		//add processes
		for(ProcessReader process : workflow.getProcesses())
			processes.add(new ProcessImpl(process,this));
	}

	public WorkflowImpl(String name) {
		this.name=name;	//required by ProcessAction, only name needed
	}

	@XmlElements({
		@XmlElement(name = "simpleAction", type = SimpleActionImpl.class),
		@XmlElement(name = "processAction", type = ProcessActionImpl.class)
	})
	public ActionImpl[] getActionList() {
		if (actions != null)
			return actions.values().toArray(new ActionImpl[0]);
		return null;
	}
	
	public void setActionList(ActionImpl[] v) {
		actions=new LinkedHashMap<String, ActionImpl>();
		for(ActionImpl a : v)
			actions.put(a.getName(), a);
	}

	@XmlElement(name = "process", type = ProcessImpl.class)
	public ProcessImpl[] getProcessList() {
		if(processes != null) 
			return processes.toArray(new ProcessImpl[0]);
		return null;
	}
	
	public void setProcessList(ProcessImpl[] v) {
		processes=new LinkedHashSet<ProcessReader>(Arrays.asList(v));
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setName(String value) {
		this.name = value;
	}


	public void addActionRef(String key,SimpleActionImpl ref) {
		WorkflowMonitorFactoryImpl.addRef(actionRefs, key, ref);
	}

	@Override
	public Set<ActionReader> getActions() {
		return new LinkedHashSet<ActionReader>(actions.values());
	}

	@Override
	public Set<ProcessReader> getProcesses() {
		return processes;
	}

	@Override
	public ActionReader getAction(String name) {
		return actions.get(name);
	}

	@Override
	public void beforeUnmarshal(Object parent) {
		monitor=(WorkflowMonitorImpl) parent;
	}

	private void linkActions() {
		//link actions with SimpleAction.possibleNextActions in actionRefs
		
		for(String name:actionRefs.keySet()) {
			ActionImpl action=actions.get(name);
			for(SimpleActionImpl simple : actionRefs.get(name))
				simple.setPossibleNextAction(action);
		}
	}

	@Override
	public void afterUnmarshal(Object parent) {
		linkActions();
		checkRoles();
	}

	private void checkRoles() {
		for(ProcessReader process : processes)
			for(ActionStatusReader status :process.getStatus()) {
				Actor actor=status.getActor();
				if(actor!=null && !getAction(status.getActionName()).getRole().equals(
						actor.getRole()))
					throw new WorkflowMonitorError(
							ERROR_ROLE + " between " +status.getActionName()+" and "+actor.getName());
			}
	}

	public WorkflowMonitorImpl getMonitor() {
		return monitor;
	}

}
