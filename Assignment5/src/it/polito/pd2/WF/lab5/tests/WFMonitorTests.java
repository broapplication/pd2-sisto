package it.polito.pd2.WF.lab5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.polito.pd2.WF.*;

import java.util.Comparator;
import java.util.Iterator;
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
    
    class ProcessReaderComparator implements Comparator<ProcessReader> {
        public int compare(ProcessReader f0, ProcessReader f1) {
        	return f0.getStartTime().compareTo(f1.getStartTime());
        }
    }

    
	private static WorkflowMonitor controlWorkflowMonitor;	// reference implementation
	private static WorkflowMonitor testWorkflowMonitor;		// implementation under test
	// testcase not needed (it does not influence the way tests are done)
	// private static long testcase;
	
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.Random.WorkflowMonitorFactoryImpl");

        controlWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();

        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.sol5.WorkflowMonitorFactoryImpl");

        testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
        // testcase not needed (it does not influence the way tests are done)
        // testcase = Long.getLong("it.polito.pd2.WF.Random.testcase").longValue();
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
        compareActionSets(fc.getActions(), ft.getActions());
	}

	private void compareActionSets(Set<ActionReader> asc,
			Set<ActionReader> ast) {
		
        if ((asc == null) && (ast != null) || (asc != null) && (ast == null)) {
            fail("getActions returns null when it should return non-null or vice versa");
            return;
        }

        if ((asc == null) && (ast == null)) {
            assertTrue("There are no Actions!", true);
            return;
        }
        
        assertEquals("Wrong Number of Actions", asc.size(), ast.size());
        
        TreeSet<ActionReader> tsc = new TreeSet<ActionReader>(new ActionReaderComparator());
        TreeSet<ActionReader> tst = new TreeSet<ActionReader>(new ActionReaderComparator());
    
        tsc.addAll(asc);
        tst.addAll(ast);
        
        Iterator<ActionReader> ic = tsc.iterator();
        Iterator<ActionReader> it = tst.iterator();

        while (ic.hasNext() && it.hasNext()) {
        	compareActionReader(ic.next(),it.next());
        }
	}

	private void compareActionReader(ActionReader arc, ActionReader art) {
		assertNotNull(arc);
        assertNotNull(art);

        compareString(arc.getName(), art.getName(), "action name");
        compareString(arc.getRole(), art.getRole(), "action role");
        assertEquals("Wrong 'automatically instantiated' attribute", arc.isAutomaticallyInstantiated(), art.isAutomaticallyInstantiated());
	}
	
}
