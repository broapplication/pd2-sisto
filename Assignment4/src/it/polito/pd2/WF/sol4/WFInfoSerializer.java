package it.polito.pd2.WF.sol4;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowMonitorFactory;

import java.io.File;
import java.io.PrintStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class WFInfoSerializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
            System.err.println("Usage: java WFInfoSerializer filename");
            System.exit(1);
        }
		
		try {
			File fileOutput=new File(args[0]);
			JAXBContext jc=JAXBContext.newInstance(WorkflowMonitorImpl.class);
			WorkflowMonitorFactory WMfactory = WorkflowMonitorFactory.newInstance();
			WorkflowMonitor monitor = WMfactory.newWorkflowMonitor();			
			WorkflowMonitorImpl ris=new WorkflowMonitorImpl(monitor);
			
			Marshaller m=jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File(fileOutput.getParent(),"wfInfo.xsd"));
			m.setSchema(schema);
			
			m.marshal(ris, fileOutput);
			m.marshal(ris, new PrintStream(System.out));
		} catch (JAXBException e) {
			System.out.println("JAXBException: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (SAXException e) {
			System.out.println("SAXException: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (WorkflowMonitorError e) {
			System.out.println("WorkflowMonitorError: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		

	}

}
