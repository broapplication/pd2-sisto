package it.polito.pd2.WF.lab6.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polito.pd2.WF.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class WFMonitorTests {
    
    class ActionReaderComparator implements Comparator<ActionReader> {
        public int compare(ActionReader f0, ActionReader f1) {
        	return f0.getName().compareTo(f1.getName());
        }
    }
    
    class WorkflowReaderComparator implements Comparator<WorkflowReader> {
        public int compare(WorkflowReader f0, WorkflowReader f1) {
        	return f0.getName().compareTo(f1.getName());
        }
    }
    
	private static WorkflowMonitor controlWorkflowMonitor;	// reference implementation
	private static WorkflowMonitor testWorkflowMonitor;		// implementation under test
	
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.Random.WorkflowMonitorFactoryImpl");

        controlWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();

        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.sol6.client1.WorkflowMonitorFactoryImpl");

        testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
      
    }
    
    @Before
    public void setUp() throws Exception {
        assertNotNull(controlWorkflowMonitor);
        assertNotNull(testWorkflowMonitor);
    }

	private void compareString(String sc, String st, String string) {
		assertNotNull("NULL "+string, st);
        assertEquals("Wrong "+string, sc, st);		
	}
	
    @Test
    public final void testGetWorkflows() {
			Set<WorkflowReader> s1 = controlWorkflowMonitor.getWorkflows();
			Set<WorkflowReader> s2 = testWorkflowMonitor.getWorkflows();
	        if ((s1 == null) && (s2 != null) || (s1 != null) && (s2 == null)) {
	            fail("getWorkflows returns null when it should return non-null or vice versa");
	            return;
	        }

	        if ((s1 == null) && (s2 == null)) {
	            assertTrue("There are no Workflows!", true);
	            return;
	        }
	        
	        assertEquals("Wrong Number of Workflows", s1.size(), s2.size());
	        
	        TreeSet<WorkflowReader> ts1 = new TreeSet<WorkflowReader>(new WorkflowReaderComparator());
	        TreeSet<WorkflowReader> ts2 = new TreeSet<WorkflowReader>(new WorkflowReaderComparator());
	    
	        ts1.addAll(s1);
	        ts2.addAll(s2);
	        
	        Iterator<WorkflowReader> i1 = ts1.iterator();
	        Iterator<WorkflowReader> i2 = ts2.iterator();

	        while (i1.hasNext() && i2.hasNext()) {
	        	compareWorkflowReader(i1.next(),i2.next());
	        }
    }

	private void compareWorkflowReader(WorkflowReader fc, WorkflowReader ft) {
		assertNotNull(fc);
        assertNotNull(ft);

        compareString(fc.getName(), ft.getName(), "workflow name");
        compareProcessSets(fc.getProcesses(), ft.getProcesses());
	}
	
    @Test
    public final void testGetProcesses() {
			Set<ProcessReader> l1 = controlWorkflowMonitor.getProcesses();
			Set<ProcessReader> l2 = testWorkflowMonitor.getProcesses();
	        compareProcessSets(l1, l2);
    }

	private void compareProcessSets(Set<ProcessReader> l1, Set<ProcessReader> l2) {
		if ((l1 == null) && (l2 != null) || (l1 != null) && (l2 == null)) {
		    fail("getProcesses returns null when it should return non-null or vice versa");
		    return;
		}

		if ((l1 == null) && (l2 == null)) {
		    assertTrue("There are no processes!", true);
		    return;
		}
		
		assertEquals("Wrong Number of processes", l1.size(), l2.size());
	}
}
