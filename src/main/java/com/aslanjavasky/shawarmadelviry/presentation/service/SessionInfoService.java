package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.IUser;
import com.aslanjavasky.shawarmadelviry.domain.model.User;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.OrderDto;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.UserDto;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
@Data
public class SessionInfoService {
    private String username;
    private String phone;
    private String password;
    private String address;
    private String email;
    private String telegram;
//    @Size(min=1, message = "Please, select at least 1 item to place an order.")
    private List<IMenuItem> cart = new ArrayList<>();

    public BigDecimal getTotalPrice(){
        return cart.stream().map(IMenuItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public IUser getUser() {
        User user=new User();
        user.setName(username);
        user.setTelegram(telegram);
        user.setAddress(address);
        user.setPhone(phone);
        user.setEmail(email);
        return user;
    }

    public void setUserFields(UserDto userDto) {
        this.username=userDto.getName();
        this.password=userDto.getPassword();
        this.address=userDto.getAddress();
        this.phone=userDto.getPhone();
        this.email=userDto.getEmail();
        this.telegram=userDto.getTelegram();
    }

    public void setInfoFromOrderDto(OrderDto orderDto) {
        username=orderDto.getUsername();
        phone=orderDto.getPhone();
        address=orderDto.getAddress();
    }
}
