package it.polito.pd2.WF.sol4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.polito.pd2.WF.ProcessActionReader;
import it.polito.pd2.WF.WorkflowReader;


/**
 * <p>Java class for processActionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processActionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/wfInfo}actionType">
 *       &lt;attribute name="actionWorkflow" use="required" type="{http://www.example.org/wfInfo}idType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processActionType")
public class ProcessActionImpl extends ActionImpl implements
ProcessActionReader, Unmarshallable {
	@XmlAttribute(name = "actionWorkflow", required = true)
	@XmlJavaTypeAdapter(AdapterWorkflowReader .class)
	protected WorkflowImpl actionWorkflow;

	public ProcessActionImpl(ProcessActionReader action, WorkflowImpl workflow) {
		if(action==null)
			return;
		setName(action.getName());
		setRole(action.getRole());
		setAutomaticallyIstantiated(action.isAutomaticallyInstantiated());
		enclosingWorkflow=workflow;
		workflow.getMonitor().addWorkflowRef(
				action.getActionWorkflow().getName(), this);
	}

	public ProcessActionImpl() {
	}

	@Override
	public void beforeUnmarshal(Object parent) {
		enclosingWorkflow=(WorkflowImpl) parent;

	}


	/**
	 * Gets the value of the actionWorkflow property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	@Override
	public WorkflowReader getActionWorkflow() {
		return actionWorkflow;
	}

	/**
	 * Sets the value of the actionWorkflow property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setActionWorkflow(WorkflowImpl value) {
		this.actionWorkflow = value;
	}


	@Override
	public void afterUnmarshal(Object parent) {
		((WorkflowImpl)enclosingWorkflow).getMonitor().addWorkflowRef(
				getActionWorkflow().getName(), this);

	}

	public static class AdapterWorkflowReader
	extends XmlAdapter<String, WorkflowReader>
	{

		public WorkflowReader unmarshal(String value) {
			return new WorkflowImpl(value);
		}

		public String marshal(WorkflowReader value) {
			return value.getName();
		}

	}

}
