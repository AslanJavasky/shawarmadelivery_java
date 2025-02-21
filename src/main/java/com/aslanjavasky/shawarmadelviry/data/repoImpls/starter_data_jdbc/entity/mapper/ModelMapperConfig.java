package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IDelivery;
import com.aslanjavasky.shawarmadelviry.domain.model.IOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        //for entity Orders
        modelMapper.typeMap(IOrder.class, OrderEntity.class)
                .addMapping(src -> src.getUser() != null ? src.getUser().getId() : null,
                        OrderEntity::setUserId);

        modelMapper.typeMap(OrderEntity.class, IOrder.class)
                .addMappings(mapping -> {
                    mapping.skip(IOrder::setUser);
                    mapping.skip(IOrder::setItemList);
                });
        //for entity Deliveries
        modelMapper.typeMap(IDelivery.class, DeliveryEntity.class)
                .addMapping(src -> src.getOrder() != null ? src.getOrder().getId() : null,
                        DeliveryEntity::setOrderId);

        modelMapper.typeMap(DeliveryEntity.class, IDelivery.class)
                .addMappings(mappings -> {
                    mappings.skip(IDelivery::setOrder);
                });
        return modelMapper;
    }

}
