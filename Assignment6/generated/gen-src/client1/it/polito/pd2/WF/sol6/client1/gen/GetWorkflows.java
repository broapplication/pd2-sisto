
package it.polito.pd2.WF.sol6.client1.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getWorkflows complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getWorkflows">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://pad.polito.it/Workflow}Name" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="processField" type="{http://pad.polito.it/Workflow}processFieldType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWorkflows", propOrder = {
    "name",
    "processField"
})
public class GetWorkflows {

    protected List<String> name;
    protected ProcessFieldType processField;

    /**
     * Gets the value of the name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getName() {
        if (name == null) {
            name = new ArrayList<String>();
        }
        return this.name;
    }

    /**
     * Gets the value of the processField property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessFieldType }
     *     
     */
    public ProcessFieldType getProcessField() {
        return processField;
    }

    /**
     * Sets the value of the processField property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessFieldType }
     *     
     */
    public void setProcessField(ProcessFieldType value) {
        this.processField = value;
    }

}