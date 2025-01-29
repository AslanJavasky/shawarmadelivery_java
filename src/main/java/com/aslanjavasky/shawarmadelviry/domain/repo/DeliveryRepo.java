package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;

public interface DeliveryRepo {
    IDelivery saveDelivery(IDelivery delivery);
    IDelivery updateDelivery(IDelivery delivery);
    IDelivery getDeliveryById(Long id);
}
