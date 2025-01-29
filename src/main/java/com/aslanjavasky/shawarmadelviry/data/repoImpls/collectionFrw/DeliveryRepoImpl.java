package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.Delivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DeliveryRepoImpl implements DeliveryRepo {

    private final List<IDelivery> deliveries = new ArrayList();
    private final AtomicLong nextId=new AtomicLong(1);

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        delivery.setId(nextId.getAndIncrement());
        deliveries.add(delivery);
        return delivery;
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        int index=deliveries.indexOf(delivery);
        if (index != -1) deliveries.set(index, delivery);
        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        return deliveries.stream()
                .filter(del -> del.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
