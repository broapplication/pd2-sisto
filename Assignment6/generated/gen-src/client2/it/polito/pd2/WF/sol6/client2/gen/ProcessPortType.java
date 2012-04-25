
package it.polito.pd2.WF.sol6.client2.gen;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * Interfaccia del servizio per il controllo di un processo.
 * Permette di prendere in carico un'azione o di terminarla.
 * Per effettuare le operazioni, ottenere prima un client ID da usare nei messaggi.
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ProcessPortType", targetNamespace = "http://pad.polito.it/Workflow")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ProcessPortType {


    /**
     * Prendi in carico un'azione tra quelle disponibili per il processo.
     * Se la richiesta va a buon fine, viene restituito lo stato dell'azione presa in carico.
     * Se i parametri di ingresso non sono corretti, oppure se il servizio non pu� rispondere, viene restituito un errore.
     * 
     * @param requestID
     * @param aCode
     * @param clientID
     * @param pCode
     * @param actor
     * @return
     *     returns it.polito.pd2.WF.sol6.client2.gen.ActionStatusType
     * @throws ActionFault
     * @throws ActorFault
     * @throws UnknownCode
     * @throws DifferentRole_Exception
     * @throws GenericFault_Exception
     */
    @WebMethod(action = "http://pad.polito.it/Workflow/takeInCharge")
    @WebResult(name = "actionStatus", targetNamespace = "")
    @RequestWrapper(localName = "takeInCharge", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TakeInCharge")
    @ResponseWrapper(localName = "takeInChargeResponse", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TakeInChargeResponse")
    public ActionStatusType takeInCharge(
        @WebParam(name = "clientID", targetNamespace = "")
        String clientID,
        @WebParam(name = "requestID", targetNamespace = "")
        int requestID,
        @WebParam(name = "PCode", targetNamespace = "")
        String pCode,
        @WebParam(name = "ACode", targetNamespace = "")
        String aCode,
        @WebParam(name = "actor", targetNamespace = "")
        ActorType actor)
        throws ActionFault, ActorFault, DifferentRole_Exception, GenericFault_Exception, UnknownCode
    ;

    /**
     * Termina un'azione tra quelle attive del processo.
     * E' possibile specificare le successive azioni da istanziare (se l'azione terminata � di tipo semplice).
     * Se la richiesta va a buon fine, viene restituito il tempo di terminazione, assieme a tutte le modifiche 
     * che la terminazione ha apportato: per le azioni semplici, lo stato delle nuove azioni istanziate (richieste in ingresso),
     * mentre per le azioni di processo, il nuovo processo creato.
     * Se i parametri di ingresso non sono corretti, oppure se il servizio non pu� rispondere, viene restituito un errore.
     * 
     * @param nextAction
     * @param nextProcess
     * @param requestID
     * @param aCode
     * @param clientID
     * @param terminationTime
     * @param pCode
     * @param nextActionStatus
     * @throws ActionFault
     * @throws UnknownCode
     * @throws GenericFault_Exception
     * @throws UnknownNames_Exception
     */
    @WebMethod(action = "http://pad.polito.it/Workflow/terminateAction")
    @RequestWrapper(localName = "terminateAction", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TerminateAction")
    @ResponseWrapper(localName = "terminateActionResponse", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TerminateActionResponse")
    public void terminateAction(
        @WebParam(name = "clientID", targetNamespace = "")
        String clientID,
        @WebParam(name = "requestID", targetNamespace = "")
        int requestID,
        @WebParam(name = "PCode", targetNamespace = "")
        String pCode,
        @WebParam(name = "ACode", targetNamespace = "")
        String aCode,
        @WebParam(name = "nextAction", targetNamespace = "")
        List<String> nextAction,
        @WebParam(name = "terminationTime", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<XMLGregorianCalendar> terminationTime,
        @WebParam(name = "nextProcess", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<ProcessSummary> nextProcess,
        @WebParam(name = "nextActionStatus", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<List<ActionStatusType>> nextActionStatus)
        throws ActionFault, GenericFault_Exception, UnknownCode, UnknownNames_Exception
    ;

    /**
     * Permette di ottenere un identificativo per contraddistinguere un client.
     * Le richieste saranno considerate univoche in base a clientID (ottenuto con questa operazione) 
     * e requestID (generato dal client ad ogni richiesta).
     * Se il servizio non pu� rispondere, viene restituito un errore.
     * 
     * @return
     *     returns java.lang.String
     * @throws GenericFault_Exception
     */
    @WebMethod(action = "http://pad.polito.it/Workflow/takeClientID")
    @WebResult(name = "clientID", targetNamespace = "")
    @RequestWrapper(localName = "takeClientID", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TakeClientID")
    @ResponseWrapper(localName = "takeClientIDResponse", targetNamespace = "http://pad.polito.it/Workflow", className = "it.polito.pd2.WF.sol6.client2.gen.TakeClientIDResponse")
    public String takeClientID()
        throws GenericFault_Exception
    ;

}
