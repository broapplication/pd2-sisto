package it.polito.pd2.WF.lab2.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polito.pd2.WF.ActionReader;
import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.WorkflowReader;

public class WFMonitorExcTests {
	
	private static WorkflowMonitor testWorkflowMonitor;		// implementation under test
	
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.sol2.WorkflowMonitorFactoryImpl");
        testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
    }
    
    @Before
    public void setUp() throws Exception {
        assertNotNull(testWorkflowMonitor);
    }
    
    @Test
    public final void testGetWorkflowWithMalformedName() {
    	String mname = "1MALFORMED";
    	WorkflowReader res = testWorkflowMonitor.getWorkflow(mname);
    	assertNull("getWorkflow returns a non-null workflow when it should not", res);
    }
    
    @Test
    public final void testGetActionWithMalformedName() {
    	String mname = "M@LFORMED";
    	Set<WorkflowReader> wfs = testWorkflowMonitor.getWorkflows();
    	if (!wfs.isEmpty()) {
    		ActionReader ar = wfs.iterator().next().getAction(mname);
        	assertNull("getAction returns a non-null action when it should not", ar);
    	}
    }
}
