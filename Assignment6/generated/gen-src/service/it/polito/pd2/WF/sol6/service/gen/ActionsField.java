
package it.polito.pd2.WF.sol6.service.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actionsField.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="actionsField">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="length"/>
 *     &lt;enumeration value="list"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "actionsField")
@XmlEnum
public enum ActionsField {

    @XmlEnumValue("length")
    LENGTH("length"),
    @XmlEnumValue("list")
    LIST("list");
    private final String value;

    ActionsField(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionsField fromValue(String v) {
        for (ActionsField c: ActionsField.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
