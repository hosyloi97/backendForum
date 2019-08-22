package com.bksoftware.controller.admin.category;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.BigCategoryService;
import com.bksoftware.service.category.SmallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/admin/small-category")
@RolesAllowed("ROLE_ADMIN")
public class AdminSmallCategoryController {

    @Autowired
    private SmallCategoryService smallCategoryService;

    @Autowired
    private BigCategoryService bigCategoryService;

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<Object> createSmallCategory(
            @RequestBody SmallCategory smallCategory,
            @RequestParam("big-id") int bigId ,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("small_category");
        record.setSize(record.getSize() + 1);
        BigCategory bigCategory = bigCategoryService.findById(bigId);
        smallCategory.setBigCategory(bigCategory);
        smallCategory.setStatus(true);
        boolean result = smallCategoryService.saveSmallCategory(smallCategory);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(smallCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>("create small category fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> updateSmallCategory(@RequestBody SmallCategory smallCategory,
                                                      HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean result = smallCategoryService.saveSmallCategory(smallCategory);
        if (result) {
            return new ResponseEntity<>(smallCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>("update small category fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> deleteSmallCategory(@RequestParam("id") int id,
                                                      HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("small_category");
        record.setSize(record.getSize() - 1);
        boolean result = smallCategoryService.deleteSmallCategory(id);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>("delete small category success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete small category fail", HttpStatus.BAD_REQUEST);
    }
}
