package it.polito.pd2.WF.sol2;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowMonitorFactory;

public class WorkflowMonitorFactoryImpl extends WorkflowMonitorFactory {
	String fileInput;
	
	public WorkflowMonitorFactoryImpl() {
		fileInput=System.getProperty("it.polito.pd2.WF.sol2.WFInfo.file");
	}
	
	public WorkflowMonitor newWorkflowMonitor() throws WorkflowMonitorError {
		try {
			WorkflowMonitorParser parser=new WorkflowMonitorParser(fileInput);
			return parser.getWorkflowMonitor();
		} catch (ParserConfigurationException e) {
			throw new WorkflowMonitorError(e);
		} catch (SAXException e) {
			throw new WorkflowMonitorError(e);
		} catch (IOException e) {
			throw new WorkflowMonitorError(e);
		}
	}
	
	

}
