/**
 * 
 */
package it.polito.pd2.WF;

import java.util.Set;

/**
 * An interface for reading the data associated with a workflow.
 *
 */
public interface WorkflowReader {
	/**
	 * Gives the name of the workflow (a string made of alphanumeric characters only, where the first character is alphabetic).
	 * @return the name of the workflow
	 */
	public String getName();
	
	/**
	 * Gives the actions defined for this workflow.
	 * @return a set of interfaces for accessing information about defined actions
	 */
	public Set<ActionReader> getActions();
	
	/**
	 * Gives the known processes that instantiate this workflow.
	 * @return a set of interfaces for accessing the information of known processes that instantiate this workflow
	 */
	public Set<ProcessReader> getProcesses();
	
	/**
	 * Gives the action with the given name.
	 * @param name the name (a string made of alphanumeric characters only, where the first character is alphabetic)
	 * @return an interface for accessing information about the action with the given name
	 * or null if this action is not in the workflow.
	 */
	public ActionReader getAction(String name);
}
