package it.polito.pd2.WF.sol4;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
//import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.pd2.WF.WorkflowMonitor;
import it.polito.pd2.WF.WorkflowMonitorError;
import it.polito.pd2.WF.WorkflowMonitorFactory;

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
		
		JAXBContext jc;
		try {
			File fileInput=new File(System.getProperty("it.polito.pd2.WF.sol4.WFInfo.file"));
//			jc = JAXBContext.newInstance("it.polito.pd2.WF.sol4");
			jc=JAXBContext.newInstance(WorkflowMonitorImpl.class);
			SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(new File(fileInput.getParent(),"wfInfo.xsd"));
			Unmarshaller u = jc.createUnmarshaller();
//			try {
//				u.setProperty("com.sun.xml.internal.bind.ObjectFactory", new ObjectFactory());
//			} catch (PropertyException e) {
//				u.setProperty("com.sun.xml.bind.ObjectFactory", new ObjectFactory());
//			}
			u.setListener(new MyListener());
			u.setSchema(schema);
	
			Object o=u.unmarshal(fileInput);
			
			return (WorkflowMonitor)o;
			
			
		} catch (JAXBException e) {
			throw new WorkflowMonitorError(e);
		} catch (SAXException e) {
			throw new WorkflowMonitorError(e);
		}
		
	
	}
}
