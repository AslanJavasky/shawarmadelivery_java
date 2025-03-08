package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@Entity(name = "admins")
//@DiscriminatorValue("admin")
public class AdminEntity extends UserEntity{

    private String secretCode;
}
