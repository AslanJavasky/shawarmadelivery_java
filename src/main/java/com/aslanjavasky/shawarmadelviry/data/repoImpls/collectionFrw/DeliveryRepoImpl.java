package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryRepoImpl implements DeliveryRepo {

    private final List<Delivery> deliveries = new ArrayList();

    @Override
    public Delivery saveDelivery(Delivery delivery) {
        deliveries.add(delivery);
        return delivery;
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        int index=deliveries.indexOf(delivery);
        if (index != -1) deliveries.set(index, delivery);
        return delivery;
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveries.stream()
                .filter(del -> del.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
