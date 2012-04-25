
package it.polito.pd2.WF.sol6.service.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actionFieldType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="actionFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="name"/>
 *     &lt;enumeration value="action"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "actionFieldType")
@XmlEnum
public enum ActionFieldType {

    @XmlEnumValue("name")
    NAME("name"),
    @XmlEnumValue("action")
    ACTION("action");
    private final String value;

    ActionFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionFieldType fromValue(String v) {
        for (ActionFieldType c: ActionFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
