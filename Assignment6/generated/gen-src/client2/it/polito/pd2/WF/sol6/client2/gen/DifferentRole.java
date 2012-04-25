
package it.polito.pd2.WF.sol6.client2.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DifferentRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DifferentRole">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="expected" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="received" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DifferentRole", propOrder = {
    "expected",
    "received"
})
public class DifferentRole {

    @XmlElement(required = true)
    protected String expected;
    @XmlElement(required = true)
    protected String received;

    /**
     * Gets the value of the expected property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpected() {
        return expected;
    }

    /**
     * Sets the value of the expected property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpected(String value) {
        this.expected = value;
    }

    /**
     * Gets the value of the received property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceived() {
        return received;
    }

    /**
     * Sets the value of the received property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceived(String value) {
        this.received = value;
    }

}
