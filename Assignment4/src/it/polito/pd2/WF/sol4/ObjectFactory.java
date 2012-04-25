package it.polito.pd2.WF.sol4;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.pd2.WF.sol4.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.pd2.WF.sol4.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ActionStatusType }
     * 
     */
    public ActionStatusImpl createActionStatusType() {
        return new ActionStatusImpl();
    }

    /**
     * Create an instance of {@link ProcessType }
     * 
     */
    public ProcessImpl createProcessType() {
        return new ProcessImpl();
    }

    /**
     * Create an instance of {@link ProcessActionType }
     * 
     */
    public ProcessActionImpl createProcessActionType() {
        return new ProcessActionImpl();
    }

    /**
     * Create an instance of {@link ActorImpl }
     * 
     */
    public ActorImpl createActorType() {
        return new ActorImpl();
    }

    /**
     * Create an instance of {@link SimpleActionType }
     * 
     */
    public SimpleActionImpl createSimpleActionType() {
        return new SimpleActionImpl();
    }

    /**
     * Create an instance of {@link WorkflowMonitorType }
     * 
     */
    public WorkflowMonitorImpl createWorkflowMonitorType() {
        return new WorkflowMonitorImpl();
    }

    /**
     * Create an instance of {@link ActionType }
     * 
     */
    public ActionImpl createActionImpl() {
        return new ActionImpl();
    }

    /**
     * Create an instance of {@link WorkflowType }
     * 
     */
    public WorkflowImpl createWorkflowType() {
        return new WorkflowImpl();
    }

}
