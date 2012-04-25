
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
 * <p>Java class for terminateActionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="terminateActionResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="terminationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="nextProcess" type="{http://pad.polito.it/Workflow}processSummary"/>
 *           &lt;element name="nextActionStatus" type="{http://pad.polito.it/Workflow}actionStatusType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "terminateActionResponse", propOrder = {
    "terminationTime",
    "nextProcess",
    "nextActionStatus"
})
public class TerminateActionResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar terminationTime;
    protected ProcessSummary nextProcess;
    protected List<ActionStatusType> nextActionStatus;

    /**
     * Gets the value of the terminationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTerminationTime() {
        return terminationTime;
    }

    /**
     * Sets the value of the terminationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTerminationTime(XMLGregorianCalendar value) {
        this.terminationTime = value;
    }

    /**
     * Gets the value of the nextProcess property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessSummary }
     *     
     */
    public ProcessSummary getNextProcess() {
        return nextProcess;
    }

    /**
     * Sets the value of the nextProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessSummary }
     *     
     */
    public void setNextProcess(ProcessSummary value) {
        this.nextProcess = value;
    }

    /**
     * Gets the value of the nextActionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nextActionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNextActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionStatusType }
     * 
     * 
     */
    public List<ActionStatusType> getNextActionStatus() {
        if (nextActionStatus == null) {
            nextActionStatus = new ArrayList<ActionStatusType>();
        }
        return this.nextActionStatus;
    }

}
