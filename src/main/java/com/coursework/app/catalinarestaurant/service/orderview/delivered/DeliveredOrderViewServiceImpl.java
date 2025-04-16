package com.coursework.app.catalinarestaurant.service.orderview.delivered;

import com.coursework.app.catalinarestaurant.entity.DeliveredOrderView;
import com.coursework.app.catalinarestaurant.repository.orderview.DeliveredOrderViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveredOrderViewServiceImpl implements DeliveredOrderViewService {

    private final DeliveredOrderViewRepository repository;

    public DeliveredOrderViewServiceImpl(DeliveredOrderViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DeliveredOrderView> findAll() {
        return repository.findAll();
    }

    @Override
    public DeliveredOrderView findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        return repository.findById(id).orElse(null);
    }
}
