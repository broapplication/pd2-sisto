/**
 * 
 */
package it.polito.pd2.WF;

/**
 * An interface for accessing the description of a process action within a workflow.
 *
 */
public interface ProcessActionReader extends ActionReader {
	/**
	 * Gives the workflow whose execution corresponds to the execution of this action.
	 * @return an interface for reading information about the workflow whose execution corresponds to the execution of this action
	 */
	public WorkflowReader getActionWorkflow();

}
