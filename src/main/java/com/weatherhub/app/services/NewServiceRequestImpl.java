package com.weatherhub.app.services;

import com.weatherhub.app.models.NewServiceRequest;
import com.weatherhub.app.repositories.NewServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewServiceRequestImpl implements NewServiceRequestService {
    @Autowired
    private NewServiceRequestRepository newServiceRequestRepository;

    @Override
    public void create(NewServiceRequest newServiceRequest) {
        newServiceRequestRepository.save(newServiceRequest);
    }

    @Override
    public List<NewServiceRequest> readAll() {
        return newServiceRequestRepository.findAll();
    }

    @Override
    public NewServiceRequest read(int id) {
        return newServiceRequestRepository.getOne(id);
    }

    @Override
    public boolean update(NewServiceRequest newServiceRequest, int id) {
        if (newServiceRequestRepository.existsById(id)) {
            newServiceRequest.setId(id);
            newServiceRequestRepository.save(newServiceRequest);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (newServiceRequestRepository.existsById(id)) {
            newServiceRequestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Object[]> getStats() {
        return newServiceRequestRepository.getStats();
    }
}
