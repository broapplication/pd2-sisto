
package it.polito.pd2.WF.sol6.service.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://pad.polito.it/Workflow}Name"/>
 *         &lt;element name="automaticallyInstantiated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="role" type="{http://pad.polito.it/Workflow}Role"/>
 *         &lt;choice>
 *           &lt;element name="nextAction" type="{http://pad.polito.it/Workflow}Name" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="workflow" type="{http://pad.polito.it/Workflow}Name" minOccurs="0"/>
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
@XmlType(name = "actionType", propOrder = {
    "name",
    "automaticallyInstantiated",
    "role",
    "nextAction",
    "workflow"
})
public class ActionType {

    @XmlElement(required = true)
    protected String name;
    protected boolean automaticallyInstantiated;
    @XmlElement(required = true)
    protected String role;
    @XmlElement(nillable = true)
    protected List<String> nextAction;
    protected String workflow;

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

    /**
     * Gets the value of the automaticallyInstantiated property.
     * 
     */
    public boolean isAutomaticallyInstantiated() {
        return automaticallyInstantiated;
    }

    /**
     * Sets the value of the automaticallyInstantiated property.
     * 
     */
    public void setAutomaticallyInstantiated(boolean value) {
        this.automaticallyInstantiated = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the nextAction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nextAction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNextAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNextAction() {
        if (nextAction == null) {
            nextAction = new ArrayList<String>();
        }
        return this.nextAction;
    }

    /**
     * Gets the value of the workflow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflow() {
        return workflow;
    }

    /**
     * Sets the value of the workflow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflow(String value) {
        this.workflow = value;
    }

}
