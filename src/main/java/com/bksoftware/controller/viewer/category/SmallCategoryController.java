package com.bksoftware.controller.viewer.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.SmallCategoryService;
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
@RequestMapping("api/v1/public/small-category")
public class SmallCategoryController {

    @Autowired
    private SmallCategoryService smallCategoryService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<SmallCategory>> findAllSmallCategory(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("small_category");
        List<SmallCategory> smallCategories = smallCategoryService.findAll();
        record.setSize(smallCategories.size());
        if (smallCategories != null) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(smallCategories, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/page")
    public ResponseEntity<List<SmallCategory>> findAllSmallCategoryPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 0;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<SmallCategory> smallCategories = smallCategoryService.findAllPage(pageable);
        if (smallCategories != null) return new ResponseEntity<>(smallCategories, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/size")
    public ResponseEntity<Double> findAllNewsPageSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("small_category").getSize();
        double result = Math.ceil(pageNumber / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<SmallCategory> findSmallCategoryById(@RequestParam("id") int id,
                                                               HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        SmallCategory smallCategory = smallCategoryService.findById(id);
        if (smallCategory != null) {
            return new ResponseEntity<>(smallCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/big-category")
    public ResponseEntity<List<SmallCategory>> findAllSmallCategoryByBigCategory(
            @RequestParam("big-id") int bigId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<SmallCategory> smallCategories = smallCategoryService.findByBigCategoryId(bigId);
        if (smallCategories != null) return new ResponseEntity<>(smallCategories, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
