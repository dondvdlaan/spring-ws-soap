package dev.manyroads.XMLSOAPCLIENT;

import dev.manyroads.customers.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CustomersClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(CustomersClient.class);
    private static final String SOAP_URI = "http://localhost:8080/customers";
    private static final String CB_NAMESPACE = "http://manyroads.dev/customers";
    private static final String CB_REQUEST = "/GetAllCustomersRequest";

    public GetAllCustomersResponse getAllCustomers() {

        log.info("Requesting All Customers");

        return getResponse(new GetAllCustomersRequest());
    }


    public GetCustomerByIdResponse getCustomerById(Long custId) {

        GetCustomerByIdRequest request = new GetCustomerByIdRequest();
        request.setCustId(custId);

        log.info("Requesting CustomerById");

        return getResponse(request);
    }

    public UpdateCustomerResponse updateCustomerById(Long custId, String custFirstName, String custLastName) {

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setCustId(custId);
        request.setCustFirstName(custFirstName);
        request.setCustLastName(custLastName);

        log.info("Requesting update CustomerById");

        return getResponse(request);
    }

    public DeleteCustomerByIdResponse deleteCustomerById(Long custId) {

        DeleteCustomerByIdRequest request = new DeleteCustomerByIdRequest();
        request.setCustId(custId);

        log.info("Requesting delete CustomerById");

        return getResponse(request);
    }

    // *** Submethods **
    // TODO: implement interceptor
    // - https://stackoverflow.com/questions/59618222/handling-soap-fault-in-spring-boot-webservicetemplate/59642717#59642717
    @SuppressWarnings("unchecked")
    private <T, V> T getResponse(V request) {

        return (T) getWebServiceTemplate()
                .marshalSendAndReceive(SOAP_URI, request,
                        new SoapActionCallback(CB_NAMESPACE + CB_REQUEST));
        /*
        try {
            return (T) getWebServiceTemplate()
                    .marshalSendAndReceive(SOAP_URI, request,
                            new SoapActionCallback(CB_NAMESPACE + CB_REQUEST));
        } catch (SoapFaultClientException ex) {
            log.error("Error message: " + ex.getMessage());
            return null;
        }

         */
    }
}
