package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Delivery;
import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.repositories.DeliveryRepository;
import com.uberClone.uberClone.services.interfaces.DeliveryService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepo;

    public void createDelivery(User user){
        Delivery newDelivery = new Delivery();
        newDelivery.setUser(user);
        this.deliveryRepo.save(newDelivery);
    }

}
