package dev.manyroads.XMLCRUD.repositories;

import dev.manyroads.XMLCRUD.models.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    List<CustomerEntity> findAll();
    CustomerEntity getCustomerByCustId(Long id);
}
