package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.DeliveryInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService extends DeliveryInterractor {
    public DeliveryService(@Qualifier("DRwJT") DeliveryRepo repo) {
        super(repo);
    }
}
