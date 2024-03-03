package dev.manyroads.XMLCRUD.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * SoapFault annotation gives you the possibility to change the SOAP faultcode and message for the
 * CustomerNotFoundException class, when thrown
 */
@SoapFault(faultCode = FaultCode.CUSTOM,
        customFaultCode = "{" + CustomerNotFoundException.NAMESPACE_URI + "}CUSTOMER_NOT_FOUND",
        faultStringOrReason = "Customer does not exist")
public class CustomerNotFoundException extends RuntimeException {

    public static final String NAMESPACE_URI = "http://manyroads.dev/customers/exceptions";
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
