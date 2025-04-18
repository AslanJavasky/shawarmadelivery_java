//package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//import org.springframework.data.neo4j.core.schema.Relationship;
//
//import java.util.ArrayList;
//import java.util.UUID;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Node("User")
//public class UserEntity {
//    @Id
//    private UUID id = UUID.randomUUID();
//    private String name;
//    private String email;
//    private String password;
//    private String telegram;
//    private String phone;
//    private String address;
////    @Relationship(type = "PLACED_ORDER", direction = Relationship.Direction.OUTGOING)
////    private List<OrderEntity> orders = new ArrayList<>();
//}
