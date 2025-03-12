package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("URwCassandra")
public interface UserRepository extends CassandraRepository<UserEntity, UUID> {
    void deleteByEmail(String email);
    UserEntity findByEmail(String email);
}
