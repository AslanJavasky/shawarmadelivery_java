package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private final MenuItemRepo menuItemRepo;

    public ApplicationStartupRunner(MenuItemRepo menuItemRepo) {

        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Гиро в лаваше L", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Гиро в лаваше XL", MenuSection.MAIN_MENU, BigDecimal.valueOf(290)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Гиро в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Гиро в пите", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Люля кебаб на углях в лаваше", MenuSection.MAIN_MENU, BigDecimal.valueOf(330)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Люля на углях в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(330)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Чизбургер куринный", MenuSection.MAIN_MENU, BigDecimal.valueOf(230)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Хот-Дог", MenuSection.MAIN_MENU, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Блэкбургер", MenuSection.MAIN_MENU, BigDecimal.valueOf(230)));

        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Фри L", MenuSection.ZAKUSKI, BigDecimal.valueOf(120)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Фри XL", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"По-деревенски", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Наггетсы", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Французский Хот-Дог", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));

        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Халапеньо", MenuSection.DOBAVKI, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Сыр", MenuSection.DOBAVKI, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Фирменный от Шефа", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Томатный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Барбекю", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Сырный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Чесночный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Кисло-Сладкий", MenuSection.SAUCE, BigDecimal.valueOf(40)));

    }


}
