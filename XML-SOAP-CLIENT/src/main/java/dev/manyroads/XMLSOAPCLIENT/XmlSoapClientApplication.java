package dev.manyroads.XMLSOAPCLIENT;

import dev.manyroads.customers.wsdl.DeleteCustomerByIdResponse;
import dev.manyroads.customers.wsdl.GetAllCustomersResponse;
import dev.manyroads.customers.wsdl.GetCustomerByIdResponse;
import dev.manyroads.customers.wsdl.UpdateCustomerResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ws.soap.client.SoapFaultClientException;

@SpringBootApplication
public class XmlSoapClientApplication implements CommandLineRunner {

    CustomersClient client;

    public XmlSoapClientApplication(CustomersClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        SpringApplication.run(XmlSoapClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        getAllCustomers();

        try{
            GetCustomerByIdResponse response2 = client.getCustomerById(4L);
            System.out.println(
                    response2.getCustomer().getCustFirstName() + " " +
                            response2.getCustomer().getCustLastName());
        }catch (SoapFaultClientException ex){
            System.out.println("Foutje: " + ex.getMessage());
        }

/*
        UpdateCustomerResponse response3 = client.updateCustomerById(3L, "TaTAtut", "DuckieDuckie");
        System.out.println(response3.getStatus().getMessage());

        getAllCustomers();

        DeleteCustomerByIdResponse response4 = client.deleteCustomerById(3L);
        System.out.println(response4.getStatus().getMessage());

        getAllCustomers();

 */

    }

    // *** Submethods ***
    private void getAllCustomers() {
        GetAllCustomersResponse response = client.getAllCustomers();

        response.getCustomers().forEach(c -> {
            System.out.println(c.getCustFirstName() + " " + c.getCustLastName());
        });
    }
}

// ref https://spring.io/guides/gs/consuming-web-service#initial
// To generate WSDL files in target, run ./mvnw compile at root
