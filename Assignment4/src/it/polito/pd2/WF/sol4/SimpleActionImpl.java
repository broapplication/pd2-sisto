package it.polito.pd2.WF.sol4;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.SimpleActionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>Java class for simpleActionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simpleActionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/wfInfo}actionType">
 *       &lt;sequence>
 *         &lt;element name="possibleNextAction" type="{http://www.example.org/wfInfo}idType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleActionType", propOrder = {
		"possibleNextActionList"
})

public class SimpleActionImpl extends ActionImpl implements
SimpleActionReader, Unmarshallable {

	@XmlElement(name = "possibleNextAction", type = String.class)
	@XmlJavaTypeAdapter(AdapterActionReader .class)
	protected List<ActionImpl> possibleNextActionList;
	

	public SimpleActionImpl(SimpleActionReader action, WorkflowImpl workflow) {
		if(action==null)
			return;
		setName(action.getName());
		setRole(action.getRole());
		setAutomaticallyIstantiated(action.isAutomaticallyInstantiated());
		enclosingWorkflow=workflow;

		List<ActionImpl> nextList = getPossibleNextActionList();
		//add next actions
		for(ActionReader next : action.getPossibleNextActions()) {
			String name=next.getName();
			next=new ActionImpl();	//will be replaced after mapping
			((ActionImpl)next).setName(name);
			nextList.add((ActionImpl) next);
			workflow.addActionRef(name,this);
		}

	}

	public SimpleActionImpl() {
	}


	    /**
	     * Gets the value of the possibleNextActionList property.
	     * 
	     * <p>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the possibleNextActionList property.
	     * 
	     * <p>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getPossibleNextActionList().add(newItem);
	     * </pre>
	     * 
	     * 
	     * <p>
	     * Objects of the following type(s) are allowed in the list
	     * {@link String }
	     * 
	     * 
	     */
	    public List<ActionImpl> getPossibleNextActionList() {
	        if (possibleNextActionList == null) {
	            possibleNextActionList = new ArrayList<ActionImpl>();
	        }
	        return this.possibleNextActionList;
	    }


	

	@Override
	public Set<ActionReader> getPossibleNextActions() {
		return new LinkedHashSet<ActionReader>(Arrays.asList(getPossibleNextActionList().toArray(new ActionReader[0])));
	}

	@Override
	public void beforeUnmarshal(Object parent) {
		enclosingWorkflow=(WorkflowImpl) parent;
	}

	@Override
	public void afterUnmarshal(Object parent) {
		//adding pending links to actionRef in eclosingWorkflow
		for(ActionReader a:getPossibleNextActionList())
			((WorkflowImpl)enclosingWorkflow).addActionRef(a.getName(),this);
	}

	public void setPossibleNextAction(ActionImpl realA) {
		getPossibleNextActionList().set(possibleNextActionList.indexOf(realA), realA);
	}

	public static class AdapterActionReader
	extends XmlAdapter<String, ActionReader>
	{

		public ActionReader unmarshal(String value) {
			return new ActionImpl(value);
		}

		public String marshal(ActionReader value) {
			return value.getName();
		}

	}
}
