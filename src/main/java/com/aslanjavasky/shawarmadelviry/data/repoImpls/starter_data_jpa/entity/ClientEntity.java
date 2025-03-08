package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data @NoArgsConstructor
//@Entity(name = "clients")
//@DiscriminatorValue("client")
public class ClientEntity extends UserEntity{

    private Long loyaltyPoints;
}
