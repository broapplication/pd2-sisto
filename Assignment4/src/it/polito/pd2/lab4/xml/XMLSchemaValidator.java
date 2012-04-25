/* 
 * Validating parser for schema with error diagnostics and warnings
 * (previously DomParseVS.java)
 */
package it.polito.pd2.lab4.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLSchemaValidator{
    public static void main(String argv[])
    {
        if (argv.length != 2) {
          System.err.println("Usage: java schema xmlfilename");
          System.exit(1);
        }

        SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = sf.newSchema(new File(argv[0]));

        } catch (SAXException e) {
            System.out.println("** Fatal error: " + e);
            System.exit(1);
        }

        DocumentBuilderFactory factory =
            DocumentBuilderFactory.newInstance();

        factory.setSchema(schema);
        factory.setNamespaceAware(true);
        
        try {
           DocumentBuilder builder = factory.newDocumentBuilder();
           
           builder.setErrorHandler(new MyValidatorHandler());
           builder.parse( new File(argv[1]) );
           
      	   System.out.println("Parsing successful!");
        } catch (SAXParseException spe) {
            // Parsing error (file is not well formed)
            System.out.println("** Fatal parsing error in file "
               + spe.getSystemId());
            System.out.println("   line " + spe.getLineNumber()
               + ", column " + spe.getColumnNumber());
            System.out.println("   " + spe.getMessage() );
            
            // Print debug info 
            // Exception  x = spe;
            // if (spe.getException() != null)
            //     x = spe.getException(); // exception in user code
            // x.printStackTrace();
            System.exit(1);
            
        } catch (SAXException sxe) {
        	// Fatal error generated during parsing
        	System.out.println("Fatal error encountered during parsing.");

        	// Print debug info 
            // Exception  x = sxe;
            // if (sxe.getException() != null)
            //     x = sxe.getException(); // exception in user code
            // x.printStackTrace();
            System.exit(1);

        } catch (ParserConfigurationException pce) {
            // Error in parser configuration
        	System.out.println("Parser configuration error. Unable to proceed.");
            
        	// Print debug info
        	// pce.printStackTrace();
            System.exit(1);

        } catch (IOException ioe) {
        	// Read error on file 
        	System.out.println("Fatal error: Unable to read file.");
        	
        	// Print debug info
        	// ioe.printStackTrace();
            System.exit(1);
        }
    } // main
}

class MyValidatorHandler extends org.xml.sax.helpers.DefaultHandler {
	
  // Validation errors are treated as fatal
  public void error (SAXParseException e) throws SAXParseException
  {
    throw e;
  }

  // Warnings are displayed (without terminating)
  public void warning (SAXParseException e) throws SAXParseException
  {
    System.out.println("** Warning in file "
            + e.getSystemId());
    System.out.println("   line " + e.getLineNumber()
       + ", column " + e.getColumnNumber());
    System.out.println("   " + e.getMessage() );
  }
}
