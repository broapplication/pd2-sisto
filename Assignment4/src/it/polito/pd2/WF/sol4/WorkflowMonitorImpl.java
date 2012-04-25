package it.polito.pd2.WF.sol4;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowReader;
/**
 * <p>Java class for workflowMonitorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="workflowMonitorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="workflow" type="{http://www.example.org/wfInfo}workflowType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "workflowMonitorType", propOrder = {
	    "workflowList"
	})
@XmlRootElement(name = "workflowMonitor",namespace="http://www.example.org/wfInfo")
public class WorkflowMonitorImpl implements
		WorkflowMonitor, Unmarshallable {
	
	@XmlTransient
	private Map<String, WorkflowImpl> workflows;
	@XmlTransient
	private Map<String, List<ProcessActionImpl>> workflowRefs;
	
	@XmlElement(name="workflow", type = WorkflowImpl.class)	
	public WorkflowImpl[] getWorkflowList() {
		if(workflows!=null)
			return  workflows.values().toArray(new WorkflowImpl[0]);
		
		return null;
	}
	
	public void setWorkflowList(WorkflowImpl[] v) {
		workflows=new LinkedHashMap<String, WorkflowImpl>();
		for(WorkflowImpl wf : v)
			workflows.put(wf.getName(), wf);
	}
	
	
	
	public WorkflowMonitorImpl() {
		this(null);
		
	}

	public WorkflowMonitorImpl(WorkflowMonitor monitor) throws WorkflowMonitorError {
		workflows=new LinkedHashMap<String, WorkflowImpl>();
		workflowRefs=new HashMap<String, List<ProcessActionImpl>>();
		if(monitor==null)
			return;

		
		//add workflows
		for(WorkflowReader wr : monitor.getWorkflows())
			workflows.put(wr.getName(),new WorkflowImpl(wr,this));

		linkWorkflows();
	}


	public void addWorkflowRef(String key, ProcessActionImpl ref) {
		WorkflowMonitorFactoryImpl.addRef(workflowRefs, key, ref);
	}
	
	
	@Override
	public Set<WorkflowReader> getWorkflows() {
		return new LinkedHashSet<WorkflowReader>(workflows.values());
	}

	@Override
	public WorkflowReader getWorkflow(String name) {
		return workflows.get(name);
	}

	@Override
	public Set<ProcessReader> getProcesses() {		
		Set<ProcessReader> processes = new LinkedHashSet<ProcessReader>();
		for(WorkflowImpl workflow : workflows.values())
			processes.addAll(workflow.getProcesses());
		return processes;
	}

	@Override
	public void beforeUnmarshal(Object parent) {
	}

	private void linkWorkflows() {
		for(String name:workflowRefs.keySet()) {
			WorkflowImpl workflow=workflows.get(name);
			for(ProcessActionImpl action: workflowRefs.get(name))
				action.setActionWorkflow(workflow);
		}
		
	}

	@Override
	public void afterUnmarshal(Object parent) {
		linkWorkflows();
	}

}
