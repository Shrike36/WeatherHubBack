package com.weatherhub.app.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "newServiceRequest")
public class NewServiceRequest {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "newServiceRequestIdSeq", sequenceName = "newServiceRequest_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newServiceRequestIdSeq")
    private Integer id;

    @NotEmpty
    @Column(name = "serviceName",  nullable=false)
    private String serviceName;

    public NewServiceRequest(String serviceName) {
        this.serviceName = serviceName;
    }

    public NewServiceRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewServiceRequest that = (NewServiceRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName);
    }

    @Override
    public String toString() {
        return "NewServiceRequest{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
