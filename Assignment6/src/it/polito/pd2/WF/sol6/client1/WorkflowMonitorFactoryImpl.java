package it.polito.pd2.WF.sol6.client1;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowMonitorFactory;
import it.polito.pd2.WF.sol6.client1.gen.UnknownNames_Exception;
import it.polito.pd2.WF.sol6.client1.gen.WorkflowInfoPortType;
import it.polito.pd2.WF.sol6.client1.gen.WorkflowInfoService;
import it.polito.pd2.WF.sol6.client1.gen.WorkflowType;
import it.polito.pd2.WF.sol6.client1.gen.ProcessFieldType;


public class WorkflowMonitorFactoryImpl extends WorkflowMonitorFactory {
	
	public static <K, R> void addRef(Map<K, List<R>> map,K key,R ref) {
		
		List<R> l = map.get(key);
		if(l==null)
			map.put(key, l=new LinkedList<R>());
		l.add(ref);
	}
	
	private static URL urlNextPort(URL u) throws MalformedURLException {
		int port = u.getPort();
		if(port == -1)
			port=80;
		return new URL(u.getProtocol() + "://" + u.getHost() + ":" +
				Integer.toString(port+1) + u.getFile());
	}
	
	@Override
	public WorkflowMonitor newWorkflowMonitor() throws WorkflowMonitorError {
		WorkflowInfoService service;

		try {
			service=new WorkflowInfoService();			
			WorkflowInfoPortType wfInfo = service.getWorkflowInfoPort();
			
			String strURL = System.getProperty("it.polito.pd2.WF.sol6.URL");
			if(strURL != null) {
				URL url = new URL(strURL);
				BindingProvider bp = (BindingProvider) wfInfo;
				bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlNextPort(url).toExternalForm());
			}

			
			List<String> wfnames = wfInfo.getWorkflowNames();
			System.out.println(wfnames);
			List<WorkflowType> workflows = wfInfo.getWorkflows(wfnames,ProcessFieldType.PROCESS_SUMMARY);
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
