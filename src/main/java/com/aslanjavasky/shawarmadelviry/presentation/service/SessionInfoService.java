package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.List;

@Service
@SessionScope
@Data
public class SessionInfoService {
    private String username;
    private String phone;
    private String address;
    private String email;
    private List<MenuItem> cart;

    public void setUserInfo(IUser user){
        setUsername(user.getName());
        setPhone(user.getPhone());
        setAddress(user.getAddress());
        setEmail(user.getEmail());
    }
    public BigDecimal getTotalPrice(){
        return cart.stream().map(MenuItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
