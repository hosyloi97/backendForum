package com.bksoftware.controller.admin.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.Menu;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/admin/menu")
@RolesAllowed("ROLE_ADMIN")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RecordService recordService;


    @PostMapping
    public ResponseEntity<Object> createMenu(
            @RequestBody Menu menu,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("menu");
        record.setSize(record.getSize() + 1);
        menu.setStatus(true);
        boolean result = menuService.saveMenu(menu);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        }
        return new ResponseEntity<>("create menu fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> updateMenu(
            @RequestBody Menu menu
            ,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean result = menuService.saveMenu(menu);
        if (result) return new ResponseEntity<>(menu, HttpStatus.OK);
        return new ResponseEntity<>("update menu fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> deleteMenu(
            @RequestParam("id") int id,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("menu");
        record.setSize(record.getSize() - 1);
        boolean result = menuService.deleteMenu(id);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>("delete menu success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete menu fail", HttpStatus.BAD_REQUEST);
    }
}
