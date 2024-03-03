package dev.manyroads.XMLCRUD.config;

import dev.manyroads.customers.ResponseStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ResponseStatus responseMessage(){
        return new ResponseStatus();
    }
}
