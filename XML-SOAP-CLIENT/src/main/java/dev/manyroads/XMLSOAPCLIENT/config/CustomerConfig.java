package dev.manyroads.XMLSOAPCLIENT.config;

import dev.manyroads.XMLSOAPCLIENT.CustomersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CustomerConfig {

    private static final String SOAP_URI = "http://localhost:8080/";


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // this package must match the package in the <generatePackage> specified in pom.xml
        marshaller.setContextPath("dev.manyroads.customers.wsdl");
        return marshaller;
    }

    @Bean
    public CustomersClient customersClient(Jaxb2Marshaller marshaller){

        CustomersClient client = new CustomersClient();
        client.setDefaultUri(SOAP_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return  client;
    }
}
