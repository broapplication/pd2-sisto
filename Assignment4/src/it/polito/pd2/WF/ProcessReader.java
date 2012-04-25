/**
 * 
 */
package it.polito.pd2.WF;

import java.util.Calendar;
import java.util.List;

/**
 * An interface for accessing information about a process.
 *
 */
public interface ProcessReader {
	
	/**
	 * Gives the starting date and time of the process.
	 * @return the starting time and date as a Calendar
	 */
	public Calendar getStartTime();
	
	/**
	 * Gives the workflow executed by this process.
	 * @return an interface for accessing information about the workflow executed by this process
	 */
	public WorkflowReader getWorkflow();
	
	/**
	 * Gives the status of this process, i.e. the status of all the actions that have been instantiated
	 * so far in the process, including the ones already executed.
	 * <br>
	 * The status of each action is accessible through the interface {@link ActionStatusReader}.  

	 * @return a list of {@link ActionStatusReader}, each one giving access to the status information of a single
	 * action execution.
	 */
	public List<ActionStatusReader> getStatus();

}
