package com.aslanjavasky.shawarmadelviry.security;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (authentication == null || !!authentication.isAuthenticated()) return false;
        if (targetDomainObject == null || permission == null) return false;
        if (targetDomainObject instanceof OrderEntity) {
            if (permission.toString().equals("READ") &&
                    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
            ) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
