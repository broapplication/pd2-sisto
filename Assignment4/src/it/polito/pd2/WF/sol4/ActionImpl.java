package it.polito.pd2.WF.sol4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.WorkflowReader;

/**
 * <p>Java class for actionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="automaticallyIstantiated" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="name" use="required" type="{http://www.example.org/wfInfo}idType" />
 *       &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actionType")
@XmlSeeAlso({
    ProcessActionImpl.class,
    SimpleActionImpl.class
})

public class ActionImpl	implements ActionReader {
	
    @XmlAttribute(name = "automaticallyInstantiated")
    protected Boolean automaticallyInstantiated;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "role", required = true)
    protected String role;

	@XmlTransient
	protected WorkflowImpl enclosingWorkflow;

	public ActionImpl(String name) {
		this.name=name;
	}

	public ActionImpl() {
	}

	@Override
    public boolean isAutomaticallyInstantiated() {
        if (automaticallyInstantiated == null)
            return false;
        else
            return automaticallyInstantiated.booleanValue();
    }
	
    /**
     * Sets the value of the automaticallyIstantiated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutomaticallyIstantiated(Boolean value) {
        this.automaticallyInstantiated = value;
    }

    @Override
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

    @Override
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


	@Override
	public WorkflowReader getEnclosingWorkflow() {
		return enclosingWorkflow;
	}


	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((ActionReader)obj).getName());
	}

}
