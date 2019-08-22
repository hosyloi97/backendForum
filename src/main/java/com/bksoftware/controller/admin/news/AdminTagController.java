package com.bksoftware.controller.admin.news;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.news.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/admin/tag")
@RolesAllowed("ROLE_ADMIN")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RecordService recordService;

    @PutMapping
    public ResponseEntity<Object> updateTag(@RequestBody Tag tag,
                                            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        boolean result = tagService.saveTag(tag);
        if (result) return new ResponseEntity<>(tag, HttpStatus.OK);
        return new ResponseEntity<>("update tag fail", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> deleteTag(@RequestParam("id") int id,
                                            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("small_category");
        record.setSize(record.getSize() - 1);
        boolean result = tagService.deleteTag(id);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>("delete tag success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete tag fail", HttpStatus.NOT_FOUND);
    }
}
