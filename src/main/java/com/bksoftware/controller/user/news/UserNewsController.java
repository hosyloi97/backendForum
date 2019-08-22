package com.bksoftware.controller.user.news;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.entities.news.News;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.entities.user.AppUser;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.category.SmallCategoryService;
import com.bksoftware.service.news.NewsService;
import com.bksoftware.service.news.TagService;
import com.bksoftware.service_impl.user.AppUserService_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user/news")
@RolesAllowed("ROLE_USER")
public class UserNewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SmallCategoryService smallCategoryService;

    @Autowired
    private AppUserService_Impl appUserService;

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<Object> createNews(
            @RequestBody News news,
            @RequestParam("user-id") int userId,
            @RequestParam("small-id") int smallId,
            @RequestParam("tagString") String tagString,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("news");
        record.setSize(record.getSize() + 1);
        SmallCategory smallCategory = smallCategoryService.findById(smallId);
        AppUser appUser = appUserService.findById(userId);
        news.setSmallCategory(smallCategory);
        news.setAppUser(appUser);
        news.setTime(LocalDateTime.now());
        news.setStatus(true);
        news.setLike(0);
        String[] arrTagId = tagString.split(",");
        Set<Integer> tagIds = new HashSet<>();

        for (int i = 0; i < arrTagId.length; i++) {
            tagIds.add(Integer.parseInt(arrTagId[i]));
        }

        List<Tag> tagList = new ArrayList<>();
        tagIds.forEach(id -> {
            Tag tag = tagService.findById(id);
            if (tag != null) {
                tagList.add(tag);
            }
        });
        news.setTags(tagList);
        boolean result = newsService.saveNews(news);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        return new ResponseEntity<>("create news fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> updateNews(
            @RequestBody News news,
            @RequestParam("tagString") String tagString,
            HttpServletResponse response
    ) {
        System.out.println("sdaaaaaaaa");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String[] arrTagId = tagString.split(",");
        Set<Integer> tagIds = new HashSet<>();

        for (int i = 0; i < arrTagId.length; i++) {
            tagIds.add(Integer.parseInt(arrTagId[i]));
        }

        List<Tag> tagList = new ArrayList<>();
        tagIds.forEach(id -> {
            Tag tag = tagService.findById(id);
            if (tag != null) {
                tagList.add(tag);
            }
        });
        news.setTags(tagList);
        boolean result = newsService.saveNews(news);
        if (result) {
            return new ResponseEntity<>("update news success", HttpStatus.OK);
        }
        return new ResponseEntity<>("update news fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/delete")
    public ResponseEntity<Object> deleteNews(@RequestParam("id") int id,
                                             HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("news");
        record.setSize(record.getSize() - 1);
        boolean result = newsService.deleteNews(id);
        if (result) {
            recordService.saveRecord(record);
            return new ResponseEntity<>("delete news success", HttpStatus.OK);
        }
        return new ResponseEntity<>("delete news fail", HttpStatus.BAD_REQUEST);
    }


}
