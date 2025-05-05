package com.aslanjavasky.shawarmadelviry.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.Collection;

public class TimeBaseVoter implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        int hour = LocalDateTime.now().getHour();
        if (hour >= 9 && hour <= 22) return ACCESS_GRANTED;
        return ACCESS_DENIED;
    }
}
