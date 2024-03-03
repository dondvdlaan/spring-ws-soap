package dev.manyroads.XMLCRUD.config;

import dev.manyroads.XMLCRUD.exceptions.DetailSoapFaultDefinitionExceptionResolver;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WSConfig extends WsConfigurerAdapter {

    /**
     * Standard MessageDispatcherServlet
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/*");
    }

    /**
     * Standard bean to add DetailSoapFaultDefinitionExceptionResolver to the Spring ApplicationContext
     * @param applicationContext
     * @return
     */
    @Bean(name = "soapFaultAnnotationExceptionResolver")
    public DetailSoapFaultDefinitionExceptionResolver exceptionResolver(ApplicationContext applicationContext ){
        DetailSoapFaultDefinitionExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

        SoapFaultDefinition soapFaultDefinition = new SoapFaultDefinition();
        soapFaultDefinition.setFaultCode( SoapFaultDefinition.SERVER );
        exceptionResolver.setDefaultFault( soapFaultDefinition );

        return exceptionResolver;
    }

    /**
     * Standard Bean to define the WSDL parts
     * @param customersSchema
     * @return
     */
    @Bean(name = "customers")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CustomersPort");
        wsdl11Definition.setLocationUri("/");
        wsdl11Definition.setTargetNamespace("http://manyroads.dev/customers");
        wsdl11Definition.setSchema(customersSchema);
        return wsdl11Definition;
    }

    /**
     * Standard Bean to add the xsd file in the resource directory to the Spring ApplicationContext
     * @return
     */
    @Bean
    public XsdSchema customersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("customers.xsd"));
    }
}
