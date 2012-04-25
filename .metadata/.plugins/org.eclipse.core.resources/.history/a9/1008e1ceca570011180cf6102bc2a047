
package it.polito.pd2.WF.sol6.service.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for processSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PCode" type="{http://pad.polito.it/Workflow}PCode"/>
 *         &lt;element name="workflowName" type="{http://pad.polito.it/Workflow}Name" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="actions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="actionStatus" type="{http://pad.polito.it/Workflow}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="availableActions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="availableActionStatus" type="{http://pad.polito.it/Workflow}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="activeActions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="activeActionStatus" type="{http://pad.polito.it/Workflow}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="terminatedActions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="terminatedActionStatus" type="{http://pad.polito.it/Workflow}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processSummary", propOrder = {
    "pCode",
    "workflowName",
    "startTime",
    "actions",
    "actionStatus",
    "availableActions",
    "availableActionStatus",
    "activeActions",
    "activeActionStatus",
    "terminatedActions",
    "terminatedActionStatus"
})
public class ProcessSummary {

    @XmlElement(name = "PCode", required = true)
    protected String pCode;
    protected String workflowName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    protected Integer actions;
    @XmlElement(nillable = true)
    protected List<ActionStatusType> actionStatus;
    protected Integer availableActions;
    @XmlElement(nillable = true)
    protected List<ActionStatusType> availableActionStatus;
    protected Integer activeActions;
    @XmlElement(nillable = true)
    protected List<ActionStatusType> activeActionStatus;
    protected Integer terminatedActions;
    @XmlElement(nillable = true)
    protected List<ActionStatusType> terminatedActionStatus;

    /**
     * Gets the value of the pCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPCode() {
        return pCode;
    }

    /**
     * Sets the value of the pCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPCode(String value) {
        this.pCode = value;
    }

    /**
     * Gets the value of the workflowName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowName() {
        return workflowName;
    }

    /**
     * Sets the value of the workflowName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowName(String value) {
        this.workflowName = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setActions(Integer value) {
        this.actions = value;
    }

    /**
     * Gets the value of the actionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionStatusType }
     * 
     * 
     */
    public List<ActionStatusType> getActionStatus() {
        if (actionStatus == null) {
            actionStatus = new ArrayList<ActionStatusType>();
        }
        return this.actionStatus;
    }

    /**
     * Gets the value of the availableActions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvailableActions() {
        return availableActions;
    }

    /**
     * Sets the value of the availableActions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvailableActions(Integer value) {
        this.availableActions = value;
    }

    /**
     * Gets the value of the availableActionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availableActionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailableActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionStatusType }
     * 
     * 
     */
    public List<ActionStatusType> getAvailableActionStatus() {
        if (availableActionStatus == null) {
            availableActionStatus = new ArrayList<ActionStatusType>();
        }
        return this.availableActionStatus;
    }

    /**
     * Gets the value of the activeActions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getActiveActions() {
        return activeActions;
    }

    /**
     * Sets the value of the activeActions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setActiveActions(Integer value) {
        this.activeActions = value;
    }

    /**
     * Gets the value of the activeActionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activeActionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActiveActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionStatusType }
     * 
     * 
     */
    public List<ActionStatusType> getActiveActionStatus() {
        if (activeActionStatus == null) {
            activeActionStatus = new ArrayList<ActionStatusType>();
        }
        return this.activeActionStatus;
    }

    /**
     * Gets the value of the terminatedActions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTerminatedActions() {
        return terminatedActions;
    }

    /**
     * Sets the value of the terminatedActions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTerminatedActions(Integer value) {
        this.terminatedActions = value;
    }

    /**
     * Gets the value of the terminatedActionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the terminatedActionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerminatedActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionStatusType }
     * 
     * 
     */
    public List<ActionStatusType> getTerminatedActionStatus() {
        if (terminatedActionStatus == null) {
            terminatedActionStatus = new ArrayList<ActionStatusType>();
        }
        return this.terminatedActionStatus;
    }

}
