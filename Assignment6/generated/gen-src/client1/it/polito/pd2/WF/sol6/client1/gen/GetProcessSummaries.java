
package it.polito.pd2.WF.sol6.client1.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getProcessSummaries complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getProcessSummaries">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="restrictions">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PCode" type="{http://pad.polito.it/Workflow}PCode" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="workflowName" type="{http://pad.polito.it/Workflow}Name" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="role" type="{http://pad.polito.it/Workflow}Role" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="actor" type="{http://pad.polito.it/Workflow}actorType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="fields">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="workflowName" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="actions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
 *                   &lt;element name="availableActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
 *                   &lt;element name="activeActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
 *                   &lt;element name="terminatedActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
 *                   &lt;element name="actionFieldType" type="{http://pad.polito.it/Workflow}actionFieldType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProcessSummaries", propOrder = {
    "restrictions",
    "fields"
})
public class GetProcessSummaries {

    @XmlElement(required = true, nillable = true)
    protected GetProcessSummaries.Restrictions restrictions;
    @XmlElement(required = true, nillable = true)
    protected GetProcessSummaries.Fields fields;

    /**
     * Gets the value of the restrictions property.
     * 
     * @return
     *     possible object is
     *     {@link GetProcessSummaries.Restrictions }
     *     
     */
    public GetProcessSummaries.Restrictions getRestrictions() {
        return restrictions;
    }

    /**
     * Sets the value of the restrictions property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetProcessSummaries.Restrictions }
     *     
     */
    public void setRestrictions(GetProcessSummaries.Restrictions value) {
        this.restrictions = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * @return
     *     possible object is
     *     {@link GetProcessSummaries.Fields }
     *     
     */
    public GetProcessSummaries.Fields getFields() {
        return fields;
    }

    /**
     * Sets the value of the fields property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetProcessSummaries.Fields }
     *     
     */
    public void setFields(GetProcessSummaries.Fields value) {
        this.fields = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="workflowName" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="actions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
     *         &lt;element name="availableActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
     *         &lt;element name="activeActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
     *         &lt;element name="terminatedActions" type="{http://pad.polito.it/Workflow}actionsField" minOccurs="0"/>
     *         &lt;element name="actionFieldType" type="{http://pad.polito.it/Workflow}actionFieldType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "workflowName",
        "startTime",
        "actions",
        "availableActions",
        "activeActions",
        "terminatedActions",
        "actionFieldType"
    })
    public static class Fields {

        protected Boolean workflowName;
        protected Boolean startTime;
        protected ActionsField actions;
        protected ActionsField availableActions;
        protected ActionsField activeActions;
        protected ActionsField terminatedActions;
        protected ActionFieldType actionFieldType;

        /**
         * Gets the value of the workflowName property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isWorkflowName() {
            return workflowName;
        }

        /**
         * Sets the value of the workflowName property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setWorkflowName(Boolean value) {
            this.workflowName = value;
        }

        /**
         * Gets the value of the startTime property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isStartTime() {
            return startTime;
        }

        /**
         * Sets the value of the startTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setStartTime(Boolean value) {
            this.startTime = value;
        }

        /**
         * Gets the value of the actions property.
         * 
         * @return
         *     possible object is
         *     {@link ActionsField }
         *     
         */
        public ActionsField getActions() {
            return actions;
        }

        /**
         * Sets the value of the actions property.
         * 
         * @param value
         *     allowed object is
         *     {@link ActionsField }
         *     
         */
        public void setActions(ActionsField value) {
            this.actions = value;
        }

        /**
         * Gets the value of the availableActions property.
         * 
         * @return
         *     possible object is
         *     {@link ActionsField }
         *     
         */
        public ActionsField getAvailableActions() {
            return availableActions;
        }

        /**
         * Sets the value of the availableActions property.
         * 
         * @param value
         *     allowed object is
         *     {@link ActionsField }
         *     
         */
        public void setAvailableActions(ActionsField value) {
            this.availableActions = value;
        }

        /**
         * Gets the value of the activeActions property.
         * 
         * @return
         *     possible object is
         *     {@link ActionsField }
         *     
         */
        public ActionsField getActiveActions() {
            return activeActions;
        }

        /**
         * Sets the value of the activeActions property.
         * 
         * @param value
         *     allowed object is
         *     {@link ActionsField }
         *     
         */
        public void setActiveActions(ActionsField value) {
            this.activeActions = value;
        }

        /**
         * Gets the value of the terminatedActions property.
         * 
         * @return
         *     possible object is
         *     {@link ActionsField }
         *     
         */
        public ActionsField getTerminatedActions() {
            return terminatedActions;
        }

        /**
         * Sets the value of the terminatedActions property.
         * 
         * @param value
         *     allowed object is
         *     {@link ActionsField }
         *     
         */
        public void setTerminatedActions(ActionsField value) {
            this.terminatedActions = value;
        }

        /**
         * Gets the value of the actionFieldType property.
         * 
         * @return
         *     possible object is
         *     {@link ActionFieldType }
         *     
         */
        public ActionFieldType getActionFieldType() {
            return actionFieldType;
        }

        /**
         * Sets the value of the actionFieldType property.
         * 
         * @param value
         *     allowed object is
         *     {@link ActionFieldType }
         *     
         */
        public void setActionFieldType(ActionFieldType value) {
            this.actionFieldType = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="PCode" type="{http://pad.polito.it/Workflow}PCode" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="workflowName" type="{http://pad.polito.it/Workflow}Name" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="role" type="{http://pad.polito.it/Workflow}Role" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="actor" type="{http://pad.polito.it/Workflow}actorType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "pCode",
        "workflowName",
        "role",
        "actor"
    })
    public static class Restrictions {

        @XmlElement(name = "PCode")
        protected List<String> pCode;
        protected List<String> workflowName;
        protected List<String> role;
        protected List<ActorType> actor;

        /**
         * Gets the value of the pCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getPCode() {
            if (pCode == null) {
                pCode = new ArrayList<String>();
            }
            return this.pCode;
        }

        /**
         * Gets the value of the workflowName property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the workflowName property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getWorkflowName().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getWorkflowName() {
            if (workflowName == null) {
                workflowName = new ArrayList<String>();
            }
            return this.workflowName;
        }

        /**
         * Gets the value of the role property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the role property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRole().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getRole() {
            if (role == null) {
                role = new ArrayList<String>();
            }
            return this.role;
        }

        /**
         * Gets the value of the actor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the actor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ActorType }
         * 
         * 
         */
        public List<ActorType> getActor() {
            if (actor == null) {
                actor = new ArrayList<ActorType>();
            }
            return this.actor;
        }

    }

}
