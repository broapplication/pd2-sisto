
package it.polito.pd2.WF.sol6.client1.gen;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "ActorFault", targetNamespace = "http://pad.polito.it/Workflow")
public class ActorFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private GenericFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ActorFault(String message, GenericFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ActorFault(String message, GenericFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: it.polito.pd2.WF.sol6.client1.gen.GenericFault
     */
    public GenericFault getFaultInfo() {
        return faultInfo;
    }

}
