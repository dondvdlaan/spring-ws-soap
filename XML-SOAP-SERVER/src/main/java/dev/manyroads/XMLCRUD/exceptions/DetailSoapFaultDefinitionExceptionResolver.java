package dev.manyroads.XMLCRUD.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;

import javax.xml.namespace.QName;

/**
 * With this class you can modify the fault message to be issued. As soon as SOAP detects a failure, this class
 * is thrown
 */
@Component
public class DetailSoapFaultDefinitionExceptionResolver extends
        SoapFaultAnnotationExceptionResolver {

    public final static Logger logger = LoggerFactory.getLogger(DetailSoapFaultDefinitionExceptionResolver.class);

    public DetailSoapFaultDefinitionExceptionResolver() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {

        //private static final QName CODE = new QName("statusCode");
        //private static final QName MESSAGE = new QName("message");

        //logger.info("TEST OK !");
        logger.info("Faultcode: " + fault.getFaultCode().getLocalPart());
        logger.info(ex.getMessage());

        // In case another Fault exception is raised, you can change the Fault code and detail as below
        /*
        if (ex instanceof ServiceFaultException) {
            ServiceStatus status = ((ServiceFaultException) ex).getServiceStatus();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(status.getStatusCode());
            detail.addFaultDetailElement(MESSAGE).addText(status.getMessage());
        }

         */
    }

}
