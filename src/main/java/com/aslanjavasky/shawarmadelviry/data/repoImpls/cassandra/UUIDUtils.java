package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra;

import java.util.Random;
import java.util.UUID;

public class UUIDUtils {
    public static UUID getUUIDFromLong(Long value) {
        if (value == null) return new UUID(new Random().nextLong(),0);
        return new UUID(value, 0);
    }

    public static Long getLongFromUUID(UUID value) {
        return value.getMostSignificantBits();
    }

//    public static void main(String[] args) {
//        Long value=null;
//        UUID uuid1=getUUIDFromLong(value);
//        Long value2=getLongFromUUID(uuid1);
//        UUID uuid2=getUUIDFromLong(value2);
//        Long value3=getLongFromUUID(uuid2);
//        UUID uuid3=getUUIDFromLong(value3);
//        System.out.println(value);
//        System.out.println(uuid1);
//        System.out.println(value2);
//        System.out.println(uuid2);
//        System.out.println(value3);
//        System.out.println(uuid3);
//        System.out.println(value2.equals(value));
//        System.out.println(uuid1.equals(uuid2));
//    }

}
