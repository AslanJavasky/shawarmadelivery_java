package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;

public class DeliveryInterractor {

    private final DeliveryRepo repo;

    public DeliveryInterractor(DeliveryRepo repo) {
        this.repo = repo;
    }

    public Delivery createDelivery(Delivery delivery) {
        return repo.saveDelivery(delivery);
    }

    public Delivery changeDelivery(Delivery delivery) {
        return repo.updateDelivery(delivery);
    }

    public Delivery getDeliveryById(Long id) {
        return repo.getDeliveryById(id);
    }

}
