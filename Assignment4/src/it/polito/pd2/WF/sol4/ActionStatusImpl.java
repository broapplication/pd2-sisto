package it.polito.pd2.WF.sol4;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.Actor;

/**
 * <p>Java class for actionStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actionStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actor" type="{http://www.example.org/wfInfo}actorType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="action" use="required" type="{http://www.example.org/wfInfo}idType" />
 *       &lt;attribute name="terminationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actionStatusType")
public class ActionStatusImpl implements
ActionStatusReader, Unmarshallable {

	@XmlElement(name = "actor", type = ActorImpl.class)
	@XmlJavaTypeAdapter(AdapterActor.class)
	protected Actor actor;
	@XmlAttribute(name = "action", required = true)
	protected String actionName;
	@XmlAttribute(name = "terminationTime")
	@XmlJavaTypeAdapter(AdapterCalendar .class)
	@XmlSchemaType(name = "dateTime")
	protected Calendar terminationTime;

	@XmlTransient
	private ProcessImpl process;

	public ActionStatusImpl(ActionStatusReader status, ProcessImpl process) {
		setActionName(status.getActionName());
		this.setProcess(process);
		if(status.isTerminated())
			setTerminationTime((Calendar) status.getTerminationTime().clone());
		if(status.isTakenInCharge()) {
			this.actor=status.getActor();
		}
	}

	public ActionStatusImpl() {
	}


	@Override
	public Actor getActor() {
		return actor;
	}

	/**
	 * Sets the value of the actor property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Actor }
	 *     
	 */
	public void setActor(Actor value) {
		this.actor = value;
	}

	/**
	 * Gets the value of the actionName property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	@Override
	public String getActionName() {
		return actionName;
	}

	/**
	 * Sets the value of the actionName property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setActionName(String value) {
		this.actionName = value;
	}

	/**
	 * Gets the value of the terminationTime property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	@Override
	public Calendar getTerminationTime() {
		return terminationTime;
	}

	/**
	 * Sets the value of the terminationTime property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Calendar }
	 *     
	 */
	public void setTerminationTime(Calendar value) {
		this.terminationTime = value;
	}


	@Override
	public boolean isTakenInCharge() {
		return actor != null;
	}

	@Override
	public boolean isTerminated() {
		return terminationTime != null;
	}

	@Override
	public void beforeUnmarshal(Object parent) {
		setProcess((ProcessImpl) parent);
	}

	@Override
	public void afterUnmarshal(Object parent) {
	}

	public ProcessImpl getProcess() {
		return process;
	}

	public void setProcess(ProcessImpl process) {
		this.process = process;
	}
	
	public static class AdapterActor extends XmlAdapter<ActorImpl, Actor> {

		
		@Override
		public ActorImpl marshal(Actor v) throws Exception {
			if(v==null)
				return null;
			return new ActorImpl(v.getName(), v.getRole());
		}

		@Override
		public Actor unmarshal(ActorImpl v) throws Exception {
			return new Actor(v.getName(), v.getRole());
		}

	};
	
}
