package com.bksoftware.controller.admin.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.BigCategoryService;
import com.bksoftware.service.category.MenuService;
import com.bksoftware.service.category.SmallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/admin/big-category")
@RolesAllowed("ROLE_ADMIN")
public class AdminBigCategoryController {

    @Autowired
    private BigCategoryService bigCategoryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private SmallCategoryService smallCategoryService;

    @PostMapping
    public ResponseEntity<Object> createBigCategory(
            @RequestBody BigCategory bigCategory,
            @RequestParam("menu-id") int menuId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record recordBig = recordService.findByName("big_category");
        Record recordSmall = recordService.findByName("small_category");
        recordBig.setSize(recordBig.getSize() + 1);
        recordSmall.setSize(recordSmall.getSize() + 1);
        bigCategory.setStatus(true);


        Menu menu = menuService.findById(menuId);
        bigCategory.setMenu(menu);
        boolean result = bigCategoryService.saveBigCategory(bigCategory);
        if (result) {
            recordService.saveRecord(recordBig);
            recordService.saveRecord(recordSmall);

            SmallCategory smallCategory = new SmallCategory();
            smallCategory.setId(bigCategory.getId());
            smallCategory.setName(bigCategory.getName());
            smallCategory.setStatus(true);
            smallCategory.setBigCategory(bigCategory);
            smallCategoryService.saveSmallCategory(smallCategory);

            return new ResponseEntity<>(bigCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>("create big category fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> updateBigCategory(
            @RequestBody BigCategory bigCategory,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean result = bigCategoryService.saveBigCategory(bigCategory);
        if (result) return new ResponseEntity<>(bigCategory, HttpStatus.OK);
        return new ResponseEntity<>("update big category fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> deleteBigCategory(
            @RequestParam("id") int id,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("big_category");
        record.setSize(record.getSize() - 1);
        boolean result = bigCategoryService.deleteBigCategory(id);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>("delete big category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete big category fail", HttpStatus.BAD_REQUEST);
    }
}
