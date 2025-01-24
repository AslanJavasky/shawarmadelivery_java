package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.MenuItemInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends MenuItemInterractor {

    public MenuItemService(MenuItemRepo repo) {
        super(repo);
    }
}
