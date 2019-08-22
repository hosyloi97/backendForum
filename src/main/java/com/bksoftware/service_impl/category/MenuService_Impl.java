package com.bksoftware.service_impl.category;

import com.bksoftware.entities.category.Menu;
import com.bksoftware.repository.category.MenuRepository;
import com.bksoftware.service.category.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MenuService_Impl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    private final static Logger LOGGER = Logger.getLogger(MenuService_Impl.class.getName());

    @Override
    public Menu findById(int id) {
        try {
            Menu menu = menuRepository.findById(id);
            if (menu.isStatus()) return menu;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-menu-by-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Menu> findAll() {
        try {
            return menuRepository.findByStatus(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-menu-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveMenu(Menu menu) {
        try {
            menuRepository.save(menu);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-menu-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteMenu(int id) {
        try {
            Menu menu = findById(id);
            menu.setStatus(false);
            menuRepository.save(menu);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-menu-error : {0}", ex.getMessage());
        }
        return false;
    }
}
