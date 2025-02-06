package com.aslanjavasky.shawarmadelviry.presentation.service;

import com.aslanjavasky.shawarmadelviry.domain.interractor.MenuItemInterractor;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends MenuItemInterractor {

    public MenuItemService (@Qualifier("MRwJT") MenuItemRepo repo) {
        super(repo);
    }
}
