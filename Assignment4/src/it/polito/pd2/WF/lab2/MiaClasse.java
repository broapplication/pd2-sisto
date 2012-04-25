package it.polito.pd2.WF.lab2;

import it.polito.pd2.WF.sol4.MyListener;
import it.polito.pd2.WF.sol4.WorkflowMonitorImpl;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

public class MiaClasse {
	
	public static void main(String argv[]) {
		
		try {
			//JAXBContext jc=JAXBContext.newInstance(WorkflowMonitorImpl.class);
			JAXBContext jc=JAXBContext.newInstance("it.polito.pd2.WF.sol4");
			SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File("xsd/wfInfo.xsd"));

			//unmarshall
			Unmarshaller u = jc.createUnmarshaller();
		
			u.setListener(new MyListener());
			u.setSchema(schema);
			Object o=u.unmarshal(new File("xsd/wfInfo.xml"));

			
			//WorkflowMonitorImpl wm=(WorkflowMonitorImpl) ((JAXBElement<WorkflowMonitorType>)o).getValue();
			WorkflowMonitorImpl wm=(WorkflowMonitorImpl)o;
			//wm.getActorList().get(0).setName("aa");
			//wm.getActorList().get(1).setName("aa");

			System.out.println("unmarshalled\n");

			Marshaller m=jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setSchema(schema);
			
			//m.marshal(o, System.out);
			m.marshal(o, new File("original.xml"));
			//m.marshal(o, new DefaultHandler());
			System.out.println("\nmarshalled original\n");
			
			//ObjectFactory of=new MyObjectFactory();
			//m.marshal(of.createWorkflowMonitor(new WorkflowMonitorImpl(wm)),System.out);
			//m.marshal(of.createWorkflowMonitor(new WorkflowMonitorImpl(wm)),new File("copy.xml"));
			m.marshal(new WorkflowMonitorImpl(wm), System.out);
			System.out.println("\nmarshalled copy\n");
			
			
			
			

		} catch (org.xml.sax.SAXException se) {
			System.out.println("Unable to validate due to following error.");
			se.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}