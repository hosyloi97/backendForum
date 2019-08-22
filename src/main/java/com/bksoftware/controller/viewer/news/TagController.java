package com.bksoftware.controller.viewer.news;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.news.TagService;
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
@RequestMapping("api/v1/public/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Tag>> findAllTag(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("tag");
        List<Tag> tags = tagService.findAll();
        record.setSize(tags.size());
        if (tags != null) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/page")
    public ResponseEntity<List<Tag>> findAllTagPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Tag> tags = tagService.findAllPage(pageable);
        if (tags != null) return new ResponseEntity<>(tags, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<Tag> findNewsById(@RequestParam("id") int id,
                                            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Tag tag = tagService.findById(id);
        if (tag != null) {
            return new ResponseEntity<>(tag, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<List<Tag>> findNewsById(@RequestParam("name") String name,
                                                  HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Tag> tags = tagService.findByName(name);
        if (tags != null) {
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/size")
    public ResponseEntity<Double> findAllNewsPageSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("tag").getSize();
        System.out.println(pageNumber);
        double result = Math.ceil(pageNumber / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/news")
    public ResponseEntity<List<Tag>> findByNewsId(@RequestParam("news-id") int newsId,
                                                  HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Tag> tags = tagService.findByNews(newsId);
        if (tags != null) return new ResponseEntity<>(tags, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
