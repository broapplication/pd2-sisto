/**
 * 
 */
package it.polito.pd2.WF;

import java.util.Calendar;

/**
 * An interface for accessing information about the status of an action instance.
 * The execution status includes the following information:
 * <ul>
 * <li> if the execution of this action instance has already been taken over and by who;</li>
 * <li> if the execution of this action instance terminated and when.</li>
 * </ul>
 *
 */
public interface ActionStatusReader {
	
	/**
	 * Gives the name of the action this execution instance refers to.
	 * @return the name of the action this execution refers to (a string made of alphanumeric characters only, where the first character is alphabetic)
	 */
	public String getActionName();
	
	/**
	 * Specifies if the execution of this action instance has already been taken over.
	 * @return true if this action execution instance has already been taken over
	 */
	public boolean isTakenInCharge();
	
	/**
	 * Specifies if the execution of this action instance is terminated.
	 * @return true if the execution of this action instance is terminated
	 */
	public boolean isTerminated();
	
	/**
	 * Gives the actor who has taken over the execution of this action instance.
	 * @return the actor who has taken over the execution of this action instance
	 * @return null if the execution of this action instance has not yet been taken over
	 */
	public Actor getActor();
	
	/**
	 * Gives the time when the execution of this action instance terminated.
	 * @return a Calendar set to the time when the execution of this action instance terminated
	 * @return null if this action execution instance is not terminated
	 */
	public Calendar getTerminationTime();
}
