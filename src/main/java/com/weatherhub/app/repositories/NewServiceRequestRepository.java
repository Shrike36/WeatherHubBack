package com.weatherhub.app.repositories;

import com.weatherhub.app.models.NewServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewServiceRequestRepository extends JpaRepository<NewServiceRequest, Integer> {

    @Query( value = "select r.service_name, count(r.service_name) from new_service_request r GROUP BY r.service_name",
            nativeQuery = true)
    List<Object[]> getStats();

}
