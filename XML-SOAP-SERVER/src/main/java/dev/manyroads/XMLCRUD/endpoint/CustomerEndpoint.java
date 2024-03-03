package dev.manyroads.XMLCRUD.endpoint;

import dev.manyroads.XMLCRUD.exceptions.CustomerNotFoundException;
import dev.manyroads.XMLCRUD.models.CustomerEntity;
import dev.manyroads.XMLCRUD.repositories.CustomerRepository;
import dev.manyroads.customers.*;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.client.SoapFaultClientException;

import java.util.List;

@Endpoint
public class CustomerEndpoint {

    private static final String NAMESPACE_URI = "http://manyroads.dev/customers";

    CustomerRepository repository;
    ResponseStatus status;

    public CustomerEndpoint(CustomerRepository repository, ResponseStatus responseMessage) {
        this.repository = repository;
        this.status = responseMessage;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCustomersRequest")
    @ResponsePayload
    public GetAllCustomersResponse getAllCustomers(@RequestPayload GetAllCustomersRequest request) {

        System.out.println("Now in CustomerEndpoint getAllCustomers ");
        // Create response
        GetAllCustomersResponse response = new GetAllCustomersResponse();
        // Get CustomerEntities from DB
        List<CustomerEntity> customerEntities = repository.findAll();

        // Copy customerEntities to response customer list
        for (CustomerEntity customerEntity : customerEntities) {
            //System.out.println("customerEntity: " + customerEntity);
            Customer customer = new Customer();
            // Copy from customer entity to customer
            BeanUtils.copyProperties(customerEntity, customer);
            //System.out.println("customer: " + customer.getCustFirstName());
            response.getCustomers().add(customer);
        }
        //response.getCustomers().forEach(System.out::println);

        return response;
    }

    // TODO: add @SoapFault
    // - https://docs.spring.io/spring-ws/sites/2.0/reference/html/index/server.html
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerByIdRequest")
    @ResponsePayload
    public GetCustomerByIdResponse getCustomerById(@RequestPayload GetCustomerByIdRequest request) {

        System.out.println("Now in CustomerEndpoint getCustomersById ");
        // Create response
        GetCustomerByIdResponse response = new GetCustomerByIdResponse();

        // Get CustomerEntities from DB
        /*
            No need for try/catch block, SOAP detects automatically if return value is null.
            Concurrently DetailSoapFaultDefinitionExceptionResolver will be thrown
        */
        CustomerEntity customerEntity = repository.getCustomerByCustId(request.getCustId());

        if(customerEntity == null)
            throw new CustomerNotFoundException("Customer id: " + request.getCustId() + " not found!");

        // Create customer
        Customer customer = new Customer();

        // Copy entity to customer
        BeanUtils.copyProperties(customerEntity, customer);
        response.setCustomer(customer);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCustomerRequest")
    @ResponsePayload
    public AddCustomerResponse addCustomer(@RequestPayload AddCustomerRequest request) {

        System.out.println("Now in CustomerEndpoint addCustomer ");
        // Create response
        AddCustomerResponse response = new AddCustomerResponse();

        // Create customer entity
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustFirstName(request.getCustFirstName());
        customerEntity.setCustLastName(request.getCustLastName());

        // Save customer entity to DB
        CustomerEntity savedCustomer = repository.save(customerEntity);

        response.setStatus(composeResponse("created", savedCustomer.getCustId()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
    @ResponsePayload
    public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request) {

        System.out.println("Now in CustomerEndpoint updateCustomer ");
        // Create response
        UpdateCustomerResponse response = new UpdateCustomerResponse();

        // Retrieve customer entity
        CustomerEntity customerEntity = repository.getCustomerByCustId(request.getCustId());

        try {
            if (customerEntity == null)
                throw new CustomerNotFoundException("No such customer with id: " + request.getCustId());

            else {
                // Update customer
                customerEntity.setCustId(request.getCustId());
                customerEntity.setCustFirstName(request.getCustFirstName());
                customerEntity.setCustLastName(request.getCustLastName());
                CustomerEntity savedCustomer = repository.save(customerEntity);

                // Compose return message
                response.setStatus(composeResponse("updated", savedCustomer.getCustId()));
            }
        } catch (CustomerNotFoundException ex) {
            status.setMessage(ex.getMessage());
            response.setStatus(status);
            System.out.printf("Customer with id: %d not found.", request.getCustId());
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerByIdRequest")
    @ResponsePayload
    public DeleteCustomerByIdResponse deleteCustomerById(@RequestPayload DeleteCustomerByIdRequest request) {

        System.out.println("Now in CustomerEndpoint deleteCustomerById ");
        // Create response
        DeleteCustomerByIdResponse response = new DeleteCustomerByIdResponse();
        // Get CustomerEntitie from DB
        CustomerEntity customerEntity = repository.getCustomerByCustId(request.getCustId());

        try {
            if (customerEntity == null) throw new CustomerNotFoundException("No such customer");

            else {
                // Delete customer
                repository.delete(customerEntity);

                // Compose return message
                response.setStatus(composeResponse("deleted", request.getCustId()));
            }
        } catch (CustomerNotFoundException ex) {
            status.setMessage(ex.getMessage());
            response.setStatus(status);
            System.out.printf("Customer with id: %d not found.", request.getCustId());
        }

        return response;
    }

    // *** Submethods ***
    /*
    Compose return message
     */
    ResponseStatus composeResponse(String operation, Long custId) {

        StringBuilder sb = new StringBuilder();
        sb.append("Customer properly ");
        sb.append(operation);
        sb.append(" with id: ");
        sb.append(custId);

        status.setMessage(sb.toString());
        return status;
    }
}
