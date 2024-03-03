package dev.manyroads.XMLCRUD;

import dev.manyroads.XMLCRUD.models.CustomerEntity;
import dev.manyroads.XMLCRUD.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class XmlCrudApplication implements CommandLineRunner {

    CustomerRepository repository;

    public XmlCrudApplication(CustomerRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(XmlCrudApplication.class);

        // Checking the MySQL DB connection with the DOCKER MySQL container
        String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");

        System.out.println(("Connected to MySQL: " + mysqlUri));
    }

    @Override
    public void run(String... args) throws Exception {

        // Fill up DB with test customers
        CustomerEntity c1 = new CustomerEntity("Patito", "Duck");
        CustomerEntity c2 = new CustomerEntity("Kwik", "Duck");
        repository.save(c1);
        repository.save(c2);
        List<CustomerEntity> customers = repository.findAll();
        customers.forEach(System.out::println);


    }
}
// Check if working http://localhost:8080/customers.wsdl

// Test curl
