package com.bksoftware.service.category;

import com.bksoftware.entities.category.Menu;

import java.util.List;

public interface MenuService {

    Menu findById(int id);

    List<Menu> findAll();

    boolean saveMenu(Menu menu);

    boolean deleteMenu(int id);
}
