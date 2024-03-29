package it.polito.pd2.WF.lab2.tests;

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
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    
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
	private static long testcase;
	
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.Random.WorkflowMonitorFactoryImpl");

        controlWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();

        System.setProperty("it.polito.pd2.WF.WorkflowMonitorFactory", "it.polito.pd2.WF.sol2.WorkflowMonitorFactoryImpl");
        
        testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
        
        testcase = Long.getLong("it.polito.pd2.WF.Random.testcase").longValue();
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
		
		TreeSet<ProcessReader> ts1 = new TreeSet<ProcessReader>(new ProcessReaderComparator());
		TreeSet<ProcessReader> ts2 = new TreeSet<ProcessReader>(new ProcessReaderComparator());
   
		ts1.addAll(l1);
		ts2.addAll(l2);
		
		Iterator<ProcessReader> i1 = ts1.iterator();
		Iterator<ProcessReader> i2 = ts2.iterator();

		while (i1.hasNext() && i2.hasNext()) {
			compareProcessReader(i1.next(),i2.next());
		}
	}


	private void compareProcessReader(ProcessReader fc, ProcessReader ft) {
		assertNotNull(fc);
        assertNotNull("A null ProcessReader has been found", ft);
        
        // Test start time
        if (testcase==2) {
	        Calendar cc = fc.getStartTime();
	        Calendar ct = ft.getStartTime();
			compareTime(cc, ct);
        }
        
        // Test workflow
        WorkflowReader wc = fc.getWorkflow();
        WorkflowReader wt = ft.getWorkflow();
		assertNotNull(wc);
        assertNotNull("A null workflow reader has been found", wt);
        assertEquals("Wrong Workflow in process",wc.getName(),wt.getName());
        
        // Test status
        List<ActionStatusReader> stat1 = fc.getStatus();
        List<ActionStatusReader> stat2 = ft.getStatus();
        
//        List<String> ris=new LinkedList<String>();
//        for(ActionStatusReader a1: stat1) {
//        	ris.add(a1.getActionName());
//        }
//        System.out.println("fc:"+fc.getStartTime().getTime().toString()+ris);
//        ris=new LinkedList<String>();
//        for(ActionStatusReader a2: stat2) {
//        	ris.add(a2.getActionName());
//        }
//        System.out.println("ft:"+ft.getStartTime().getTime().toString()+ris);
        

        if (stat1!=null) {
        	assertNotNull("A null action status list has been found", stat2);
        	assertEquals("Wrong number of action status elements", stat1.size(), stat2.size());        		
        	
        	
        	if (stat1.size()>0)
        		compareActionStatus(stat1.get(0), stat2.get(0));
        	

        }
        
	}

	private void compareTime(Calendar cc, Calendar ct) {
		assertNotNull(cc);
		assertNotNull("Null start time", ct);
		
		
		// Compute lower and upper bounds for checking with precision of 1 minute
		Calendar upperBound, lowerBound;
		upperBound = (Calendar)cc.clone();
		upperBound.add(Calendar.MINUTE, 1);
		lowerBound = (Calendar)cc.clone();
		lowerBound.add(Calendar.MINUTE, -1);
		
		// Compute the condition to be checked
		boolean condition = ct.after(lowerBound) && ct.before(upperBound);
		
		assertTrue("Wrong time", condition);
	}

	private void compareActionStatus(ActionStatusReader asc, ActionStatusReader ast) {
		
		compareString(asc.getActionName(), ast.getActionName(), "action name");
		assertTrue("Wrong taken over condition in action "+asc.getActionName(), asc.isTakenInCharge()==ast.isTakenInCharge());
		assertTrue("Wrong terminated condition in action "+asc.getActionName(), asc.isTerminated()==ast.isTerminated());
		
		if(asc.isTakenInCharge()) {
			Actor at = ast.getActor();
			assertNotNull("Null actor in taken action", at);
			compareString(asc.getActor().getName(), at.getName(), "actor name");
			compareString(asc.getActor().getRole(), at.getRole(), "actor role");
		}
		
		if(asc.isTerminated()) {
			Calendar ct = ast.getTerminationTime();
			assertNotNull("Null termination time in terminated action", ct);
	        if (testcase==2) {
				compareTime(asc.getTerminationTime(), ct);
	        }
		}
	}
}
