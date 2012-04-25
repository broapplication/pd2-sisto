package it.polito.pd2.WF.sol4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowReader;

/**
 * <p>Java class for processType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actionStatus" type="{http://www.example.org/wfInfo}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="startTime" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processType")
public class ProcessImpl implements 
		ProcessReader, Unmarshallable {

    @XmlElement(name = "actionStatus", type = ActionStatusImpl.class)
    protected List<ActionStatusReader> status;
    @XmlAttribute(name = "startTime", required = true)
    @XmlJavaTypeAdapter(AdapterCalendar .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar startTime;

	@XmlTransient
	private WorkflowImpl workflow;
	
	public ProcessImpl(ProcessReader process, WorkflowImpl workflow) {
		this.workflow=workflow;
		setStartTime((Calendar)process.getStartTime().clone());
		
		this.status=new ArrayList<ActionStatusReader>();
		//add action status
		for(ActionStatusReader status : process.getStatus())
			this.status.add(new ActionStatusImpl(status,this));
	}

	public ProcessImpl() {
	}


	@Override
    public List<ActionStatusReader> getStatus() {
        if (status == null) {
            status = new ArrayList<ActionStatusReader>();
        }
        return status;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(Calendar value) {
        this.startTime = value;
    }

	
	@Override
	public WorkflowReader getWorkflow() {
		return workflow;
	}


	@Override
	public void beforeUnmarshal(Object parent) {
		workflow=(WorkflowImpl) parent;
	}

	@Override
	public void afterUnmarshal(Object parent) {
	}

}
