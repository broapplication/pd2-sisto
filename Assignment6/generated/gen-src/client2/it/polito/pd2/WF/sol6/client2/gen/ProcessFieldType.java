
package it.polito.pd2.WF.sol6.client2.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processFieldType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="processFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PCode"/>
 *     &lt;enumeration value="processSummary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "processFieldType")
@XmlEnum
public enum ProcessFieldType {

    @XmlEnumValue("PCode")
    P_CODE("PCode"),
    @XmlEnumValue("processSummary")
    PROCESS_SUMMARY("processSummary");
    private final String value;

    ProcessFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProcessFieldType fromValue(String v) {
        for (ProcessFieldType c: ProcessFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
