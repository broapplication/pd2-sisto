/**
 * 
 */
package it.polito.pd2.WF;

import java.util.Set;

/**
 * An interface for accessing the description of a simple action within a workflow.
 * <br>
 * For each simple action there is a set of next actions that can be instantiated when the action terminates.
 * When an actor signals the termination of an action, the actor may specify which new actions (included in the above set) have to be instantiated.
 *
 */
public interface SimpleActionReader extends ActionReader {
	
	/**
	 * Gives the new actions that can be instantiated at the termination of this action.
	 * @return a set of interfaces for reading information about the next possible actions to be instantiated
	 */
	public Set<ActionReader> getPossibleNextActions();
}
