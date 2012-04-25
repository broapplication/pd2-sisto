package it.polito.pd2.WF.lab5.tests;

import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.sol5.WorkflowMonitorFactoryImpl;

public class Prova {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WorkflowMonitorFactory factory=new WorkflowMonitorFactoryImpl();
		System.setProperty("it.polito.pd2.WF.sol5.URL", "http://localhost:8181/WorkflowInfoService?wsdl");
		factory.newWorkflowMonitor();
	}

}
