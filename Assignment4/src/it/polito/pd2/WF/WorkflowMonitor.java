/**
 * 
 */
package it.polito.pd2.WF;

import java.util.Set;

/**
 * An interface for monitoring Workflow data.
 *
 */
public interface WorkflowMonitor {
		/**
		 * Gives access to the set of known workflows
		 * @return a set of interfaces for reading all available workflows
		 */
		public Set<WorkflowReader> getWorkflows();
		
		/**
		 * Gives access to a single workflow given its name.
		 * @param name the workflow to get (a string made of alphanumeric characters only, where the first character is alphabetic)
		 * @return an interface for reading information about the workflow with the given name
		 * or null if a workflow with the given name is not available
		 */
		public WorkflowReader getWorkflow(String name);
		
		/**
		 * Gives access to the set of known processes.
		 * @return a set of interfaces for reading all available processes
		 */
		public Set<ProcessReader> getProcesses();
}
