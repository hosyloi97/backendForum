package com.bksoftware.controller.viewer.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/public/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Menu>> findAllMenu(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Menu> menus = menuService.findAll();
        Record record = recordService.findByName("menu");
        record.setSize(menus.size() );
        if (!menus.isEmpty()) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<Menu> findMenuById(@RequestParam("id") int id,
                                             HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Menu menu = menuService.findById(id);
        if (menu != null) {
            return new ResponseEntity<>(menu, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
