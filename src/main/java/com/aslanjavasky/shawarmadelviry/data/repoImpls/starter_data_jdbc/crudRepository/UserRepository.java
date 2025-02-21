package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepoExtCrudRepo")
public interface UserRepository extends CrudRepository<UserEntity, Long> {
//    @Query("DELETE FROM users WHERE email = :email")
    void deleteByEmail(String email);

    UserEntity findByEmail(String email);
}

//получение(поиск)
/*
SELECT FROM users WHERE email=:email
getByEmail
findByEmail
readByEmail

SELECT * FROM users WHERE telegram=:telegram -> List<UserEntity>
getByTelegram(String telegram)
findByTelegram(String telegram)
readByTelegram(String telegram)

SELECT * FROM users WHERE phone=:phone -> List<UserEntity>
getByPhone(String phone)
findByPhone(String phone)
readByPhone(String phone)

SELECT * FROM users WHERE phone=:phone OR telegram=:telegram -> List<UserEntity>
getByPhoneOrTelegram(String phone, String telegram)
findByPhoneOrTelegram(String phone, String telegram)
readByPhoneOrTelegram(String phone, String telegram)

SELECT * FROM users WHERE address:address AND phone:phone -> List<UserEntity>
getByAddressAndPhone(String address, String phone)
findByAddressAndPhone(String address, String phone)
readByAddressAndPhone(String address, String phone)

SELECT * FROM users WHERE name=:name OR email=:email -> List<UserEntity>
getByNameOrEmail(String name, String email)
findByNameOrEmail(String name, String email)
readByNameOrEmail(String name, String email)
----------------------------------------------------------------------------
- Агрегирование countBy, sumBy, avgBy, minBy, maxBy
SELECT COUNT(*) FROM users WHERE address:address AND phone:phone -> Long
countByAddressAndPhone(String address, String phone)

SELECT AVG(age) FROM users
averageAge()

SELECT SUM(id) FROM users WHERE address=:address
sumIdByAddress(String address)

SELECT MAX(age) FROM users WHERE county=:country  -> UserEntity
maxAgeByCountry(String country)

SELECT MIN(age) FROM users WHERE department=:department  -> UserEntity
minAgeByCountry(String department)

------------------------------------------------------------------------------
Операторы сравнения (<,>,>=,<=,!=,==)
SELECT COUNT(*) FROM users WHERE id > :id  -> Long
countByIdGreaterThan(Long id)

SELECT COUNT(*) FROM users WHERE id < :id  -> Long
countByIdLessThan(Long id)

SELECT COUNT(*) FROM users WHERE id >= :id  -> Long
countByIdGreaterThanEqual(Long id)

SELECT COUNT(*) FROM users WHERE id <= :id  -> Long
countByIdLessThanEqual(Long id)

SELECT COUNT(*) FROM users WHERE id = :id  -> Long
countById(Long id)

SELECT COUNT(*) FROM users WHERE id != :id  -> Long
countByIdNot(Long id)

-------------------------------------------------------------------------------------
DELETE FROM users WHERE email = :email
deleteByEmail(String email)

 */


