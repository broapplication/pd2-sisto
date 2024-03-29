package it.polito.pd2.WF.sol5;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.sol5.gen.UnknownNames_Exception;
import it.polito.pd2.WF.sol5.gen.Workflow;
import it.polito.pd2.WF.sol5.gen.WorkflowInfo;
import it.polito.pd2.WF.sol5.gen.WorkflowInfoService;

public class WorkflowMonitorFactoryImpl extends WorkflowMonitorFactory {
	
	public static <K, R> void addRef(Map<K, List<R>> map,K key,R ref) {
		
		List<R> l = map.get(key);
		if(l==null)
			map.put(key, l=new LinkedList<R>());
		l.add(ref);
	}

	
	public WorkflowMonitorFactoryImpl() {
	}
	
	@Override
	public WorkflowMonitor newWorkflowMonitor() throws WorkflowMonitorError {
		String urlProperty=System.getProperty("it.polito.pd2.WF.sol5.URL");
		WorkflowInfoService service;

		try {
			if(urlProperty==null)
				service = new WorkflowInfoService();
			else
				service= new WorkflowInfoService(
						new URL(System.getProperty("it.polito.pd2.WF.sol5.URL")),
						new QName("http://pad.polito.it/WorkflowInfo", "WorkflowInfoService"));

			WorkflowInfo wfInfo = service.getWorkflowInfoPort();
			List<String> names = wfInfo.getWorkflowNames();
			System.out.println(names);
			List<Workflow> workflows = wfInfo.getWorkflows(names);
			WorkflowMonitorImpl monitor = new WorkflowMonitorImpl(workflows);
			return monitor;			
		} catch (MalformedURLException e) {
			throw new WorkflowMonitorError(e);
		} catch (WebServiceException e) {
			throw new WorkflowMonitorError(e);
		} catch (UnknownNames_Exception e) {
			throw new WorkflowMonitorError(e);
		} catch (Exception e) {
			throw new WorkflowMonitorError(e);
		}
		
	
	}
}
