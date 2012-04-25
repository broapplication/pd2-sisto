package it.polito.pd2.WF.lab6.tests;

import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.sol6.client1.WorkflowMonitorFactoryImpl;
import it.polito.pd2.WF.sol6.client2.Client2;
import it.polito.pd2.WF.sol6.service.WorkflowServer;

public class Prova {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.Random.WorkflowMonitorFactoryImpl");
		System.setProperty("it.polito.pd2.WF.Random.seed","112233");
		System.setProperty("it.polito.pd2.WF.Random.testcase", "2");
		System.setProperty("WORKFLOW_SERVICE", "http://localhost:8181/WorkflowService");
		System.setProperty("WORKFLOW_INFO_SERVICE", "http://localhost:8182/WorkflowService");
		System.setProperty("PROCESS_SERVICE", "http://localhost:8183/WorkflowService");
		WorkflowServer.main(args);
		
		WorkflowMonitorFactory factory=new WorkflowMonitorFactoryImpl();
		System.setProperty("it.polito.pd2.WF.sol6.URL", "http://localhost:8181/WorkflowService");
		//factory.newWorkflowMonitor();
		
		String[] a=new String[2];
		
		
		a[0]="Mario Rossi";
		a[1]="Giornalista";
		Client2.main(a);
	}

}