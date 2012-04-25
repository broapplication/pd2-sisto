
package it.polito.pd2.WF.sol6.service.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for terminateAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terminateAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientID" type="{http://pad.polito.it/Workflow}ID"/>
 *         &lt;element name="requestID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PCode" type="{http://pad.polito.it/Workflow}PCode"/>
 *         &lt;element name="ACode" type="{http://pad.polito.it/Workflow}ACode"/>
 *         &lt;element name="nextAction" type="{http://pad.polito.it/Workflow}Name" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "terminateAction", propOrder = {
    "clientID",
    "requestID",
    "pCode",
    "aCode",
    "nextAction"
})
public class TerminateAction {

    @XmlElement(required = true)
    protected String clientID;
    protected int requestID;
    @XmlElement(name = "PCode", required = true)
    protected String pCode;
    @XmlElement(name = "ACode", required = true)
    protected String aCode;
    protected List<String> nextAction;

    /**
     * Gets the value of the clientID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Sets the value of the clientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientID(String value) {
        this.clientID = value;
    }

    /**
     * Gets the value of the requestID property.
     * 
     */
    public int getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     */
    public void setRequestID(int value) {
        this.requestID = value;
    }

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
     * Gets the value of the aCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACode() {
        return aCode;
    }

    /**
     * Sets the value of the aCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACode(String value) {
        this.aCode = value;
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

}
