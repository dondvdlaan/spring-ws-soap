package dev.manyroads.XMLCRUD.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue
    long custId;
    String custFirstName;
    String custLastName;

    public CustomerEntity() {
        this.custFirstName = "nothingToSee";
        this.custLastName = "nothingToSee";
    }

    public CustomerEntity(String custFirstName, String custLastName) {
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custID=" + custId +
                ", custFirstName='" + custFirstName + '\'' +
                ", custLastName='" + custLastName + '\'' +
                '}';
    }
}
