package com.aslanjavasky.shawarmadelviry.conf;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private final MenuItemRepo menuItemRepo;

    public ApplicationStartupRunner(@Qualifier("MenuItemRepoAdapter_CRUD") MenuItemRepo menuItemRepo) {

        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public void run(String... args) throws Exception {
//        menuItemRepo.deleteAll();

        menuItemRepo.saveMenuItem(
                new MenuItem(1L,"Гиро в лаваше L", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(2L,"Гиро в лаваше XL", MenuSection.MAIN_MENU, BigDecimal.valueOf(290)));
        menuItemRepo.saveMenuItem(
                new MenuItem(3L,"Гиро в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(4L,"Гиро в пите", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(5L,"Люля кебаб на углях в лаваше", MenuSection.MAIN_MENU, BigDecimal.valueOf(330)));
        menuItemRepo.saveMenuItem(
                new MenuItem(6L,"Люля на углях в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(330)));
        menuItemRepo.saveMenuItem(
                new MenuItem(7L,"Чизбургер куринный", MenuSection.MAIN_MENU, BigDecimal.valueOf(230)));
        menuItemRepo.saveMenuItem(
                new MenuItem(8L,"Хот-Дог", MenuSection.MAIN_MENU, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(9L,"Блэкбургер", MenuSection.MAIN_MENU, BigDecimal.valueOf(230)));

        menuItemRepo.saveMenuItem(
                new MenuItem(10L,"Фри L", MenuSection.ZAKUSKI, BigDecimal.valueOf(120)));
        menuItemRepo.saveMenuItem(
                new MenuItem(11L,"Фри XL", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(12L,"По-деревенски", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(13L,"Наггетсы", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));
        menuItemRepo.saveMenuItem(
                new MenuItem(14L,"Французский Хот-Дог", MenuSection.ZAKUSKI, BigDecimal.valueOf(150)));

        menuItemRepo.saveMenuItem(
                new MenuItem(15L,"Халапеньо", MenuSection.DOBAVKI, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(16L,"Сыр", MenuSection.DOBAVKI, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(17L,"Фирменный от Шефа", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(18L,"Томатный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(19L,"Барбекю", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(20L,"Сырный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(21L,"Чесночный", MenuSection.SAUCE, BigDecimal.valueOf(40)));
        menuItemRepo.saveMenuItem(
                new MenuItem(22L,"Кисло-Сладкий", MenuSection.SAUCE, BigDecimal.valueOf(40)));

        menuItemRepo.saveMenuItem(
                new MenuItem(23L,"Шаурма L", MenuSection.MAIN_MENU, BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(
                new MenuItem(24L,"Шаурма XL", MenuSection.MAIN_MENU, BigDecimal.valueOf(290)));

     }


}
