package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;

public class DeliveryInterractor {

    private final DeliveryRepo repo;

    public DeliveryInterractor(DeliveryRepo repo) {
        this.repo = repo;
    }

    public IDelivery createDelivery(IDelivery delivery) {
        return repo.saveDelivery(delivery);
    }

    public IDelivery changeDelivery(IDelivery delivery) {
        return repo.updateDelivery(delivery);
    }

    public IDelivery getDeliveryById(Long id) {
        return repo.getDeliveryById(id);
    }

}
