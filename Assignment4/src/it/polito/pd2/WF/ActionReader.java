/**
 * 
 */
package it.polito.pd2.WF;

/**
 * An interface for accessing the description of an action within a workflow.
 * <br>
 * An action is enclosed in a workflow. It is identified uniquely in the enclosing workflow by a name
 * (a string made of alphanumeric characters only, where the first character is alphabetic).
 * <br>
 * An instance of an action, once created, can be taken over for execution by an actor belonging to a particular role.
 * The termination of the execution of an action instance is signaled by the actor who has taken over the action instance.
 * An action can be of 2 different types, represented by corresponding sub-interfaces:
 * <ul>
 * <li> {@link SimpleActionReader} (a simple action that, when finished, enables the creation of other subsequent action instances)</li>
 * <li> {@link ProcessActionReader} (an action that corresponds to the execution of a whole new process)</li>
 * </ul>
 * There are actions for which an execution instance is created automatically when a process that instantiates the workflow starts.
 * 
 *
 */
public interface ActionReader {
	/**
	 * Gives the name of the action
	 * @return the name of the action (a string made of alphanumeric characters only, where the first character is alphabetic)
	 */
	public String getName();
	
	/**
	 * Gives the workflow this action belongs to
	 * @return an interface for reading information about the workflow this action belongs to
	 */
	public WorkflowReader getEnclosingWorkflow();
	
	/**
	 * Gives the role of the actors who may take over executions of this action
	 * @return the role name (an alphabetic string)
	 */
	public String getRole();
		
	/**
	 * Specifies if an instance of this action is automatically created when a process for the enclosing workflow is created.
	 * @return true if an instance of this action is automatically created
	 */
	public boolean isAutomaticallyInstantiated();
}
