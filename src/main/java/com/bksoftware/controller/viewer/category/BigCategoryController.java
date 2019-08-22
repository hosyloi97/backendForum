package com.bksoftware.controller.viewer.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.BigCategoryService;
import com.bksoftware.service.category.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/public/big-category")
public class BigCategoryController {

    @Autowired
    private BigCategoryService bigCategoryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<BigCategory>> findAllMenu() {
        Record record = recordService.findByName("big_category");
        List<BigCategory> bigCategories = bigCategoryService.findAll();
        record.setSize(bigCategories.size());
        if (bigCategories != null) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(bigCategories, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/page")
    public ResponseEntity<List<BigCategory>> findAllBigCategoryPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<BigCategory> bigCategories = bigCategoryService.findAllPage(pageable);
        if (bigCategories != null) return new ResponseEntity<>(bigCategories, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/size")
    public ResponseEntity<Double> findAllNewsPageSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("big_category").getSize();
        System.out.println(pageNumber);
        double result = Math.ceil(pageNumber / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<BigCategory> findBigCategoryById(@RequestParam("id") int id,
                                                           HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        BigCategory bigCategory = bigCategoryService.findById(id);
        if (bigCategory != null) {
            return new ResponseEntity<>(bigCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<BigCategory>> findBigCategoryByMenu(@RequestParam("menu-id") int menuId,
                                                                   HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Menu menu = menuService.findById(menuId);
        List<BigCategory> bigCategories = bigCategoryService.findByMenu(menu);
        if (bigCategories != null) return new ResponseEntity<>(bigCategories, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
