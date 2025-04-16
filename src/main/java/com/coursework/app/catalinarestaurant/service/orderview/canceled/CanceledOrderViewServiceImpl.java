package com.coursework.app.catalinarestaurant.service.orderview.canceled;

import com.coursework.app.catalinarestaurant.entity.CanceledOrderView;
import com.coursework.app.catalinarestaurant.repository.orderview.CanceledOrderViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanceledOrderViewServiceImpl implements CanceledOrderViewService {

    private final CanceledOrderViewRepository repository;

    public CanceledOrderViewServiceImpl(CanceledOrderViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CanceledOrderView> findAll() {
        return repository.findAll();
    }

    @Override
    public CanceledOrderView findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        return repository.findById(id).orElse(null);
    }
}
