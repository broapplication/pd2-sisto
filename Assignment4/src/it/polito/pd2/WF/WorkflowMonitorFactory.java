/**
 * 
 */
package it.polito.pd2.WF;


/**
 * Defines a factory API that enables applications to obtain one or more objects
 * implementing the       {@link WorkflowMonitor}       interface.
 *
 */
public abstract class WorkflowMonitorFactory {
	private static final String propertyName = "it.polito.pd2.WF.WorkflowMonitorFactory";
	
	protected WorkflowMonitorFactory() {}
	
	/**
	 * Obtain a new instance of a <tt>WorkflowMonitorFactory</tt>.
	 * 
	 * <p>
	 * This static method creates a new factory instance. This method uses the
	 * <tt>it.polito.pd2.WF.WorkflowMonitorFactory</tt> system property to
	 * determine the WorkflowMonitorFactory implementation class to load.
	 * </p>
	 * <p>
	 * Once an application has obtained a reference to a
	 * <tt>WorkflowMonitorFactory</tt> it can use the factory to obtain a new
	 * {@link WorkflowMonitor} instance.
	 * </p>
	 * 
	 * @return a new instance of a <tt>WorkflowMonitorFactory</tt>.
	 * 
	 * @throws FactoryConfigurationError if the implementation is not available 
	 * or cannot be instantiated.
	 */
	public static WorkflowMonitorFactory newInstance() throws FactoryConfigurationError {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = WorkflowMonitorFactory.class.getClassLoader();
		}

		String className = System.getProperty(propertyName);
		if (className == null)
			throw new FactoryConfigurationError("cannot create a new instance of "
							+ "a WorkflowMonitorFactory since the system property '"
							+ propertyName + "' is not defined.");

		try {
			Class<?> t = (loader != null) ? loader.loadClass(className) : 
										 Class.forName(className);

			return (WorkflowMonitorFactory) t.newInstance();
		} catch (Exception e) {
			throw new FactoryConfigurationError(e, 
							"error instantiating class '" + className + "'.");
		}
	}
	
	/**
	 * Creates a new instance of a {@link WorkflowMonitor} implementation.
	 * 
	 * @return a new instance of a {@link WorkflowMonitor} implementation.
	 * @throws WorkflowMonitorError if an implementation of {@link WorkflowMonitor} cannot be created.
	 */
	public abstract WorkflowMonitor newWorkflowMonitor() throws WorkflowMonitorError;


}
