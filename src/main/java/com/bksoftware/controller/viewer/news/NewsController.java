package com.bksoftware.controller.viewer.news;


import com.bksoftware.entities.Record;
import com.bksoftware.entities.news.News;
import com.bksoftware.entities.news.NumberNewsAndTag;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.service.RecordService;
import com.bksoftware.service.news.NewsService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/public/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private RecordService recordService;

    private static int enumTagId = 0;

    private static int enumMenuId = 0;

    private static int enumBigCategoryId = 0;

    private static int enumSmallCategoryId = 0;

    private static String enumNameSearch = "";

    @GetMapping
    public ResponseEntity<List<News>> findAllNewsPage(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Record record = recordService.findByName("news");
        List<News> newsList = newsService.findAll();
        record.setSize(newsList.size());
        if (newsList != null) {
            recordService.saveRecord(record);
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("status")
    public ResponseEntity<List<News>> findAllNewsStatusFalse(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<News> newsList = newsService.findAllNewsStatusFalse();
        if (newsList != null) {

            return new ResponseEntity<>(newsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/page")
    public ResponseEntity<List<News>> findAllNewsPage(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findAllPage(pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/size")
    public ResponseEntity<Double> findAllNewsPageSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("news").getSize();
        double result = Math.ceil(pageNumber / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<News> findNewsById(@RequestParam("id") int id,
                                             HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        News news = newsService.findById(id);
        if (news != null) {
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/menu")
    public ResponseEntity<List<News>> findAllNewsPageByMenuId(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam("menu-id") int menuId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        enumMenuId = menuId;
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findByMenu(menuId, pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size menu
    @GetMapping("/menu/size")
    public ResponseEntity<Double> findAllNewsPageByMenuIdSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double result = Math.ceil(newsService.findByMenuSize(enumMenuId) / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/big-category")
    public ResponseEntity<List<News>> findAllNewsPageByBigCategoryId(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam("big-id") int bigId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        enumBigCategoryId = bigId;
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findByBigCategory(bigId, pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size big category
    @GetMapping("/big-category/size")
    public ResponseEntity<Double> findAllNewsPageByBigCategoryIdSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double result = Math.ceil(newsService.findByBigCategorySize(enumBigCategoryId) / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Hot By Month
    @GetMapping("/hot-by-month")
    public ResponseEntity<List<News>> findAllNewsHotByMonth(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> news = newsService.findNewsByLikeWithMonth(pageable);
        if (news != null) return new ResponseEntity<>(news, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //size news and tag by big category
    @GetMapping("/big-category/news-tag/size")
    public ResponseEntity<Object> findSizeAllNewsPageByBigCategoryId(
            @RequestParam("id") int id,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<News> news = newsService.findSizeNewsAndTagByBigCategory(id);
        NumberNewsAndTag number = new NumberNewsAndTag();
        number.setNewsNumber(news.size());
        Set<Tag> tags = new HashSet<>();
        news.forEach(n -> {
            n.getTags().forEach(tag -> {
                tags.add(tag);
            });
        });

        number.setTagNumber(tags.size());
        return new ResponseEntity<>(number, HttpStatus.OK);
    }


    @GetMapping("/small-category")
    public ResponseEntity<List<News>> findAllNewsPageBySmallCategoryId(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam("big-id") int smallId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        enumSmallCategoryId = smallId;
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findBySmallCategory(smallId, pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size Small category
    @GetMapping("/small-category/size")
    public ResponseEntity<Double> findAllNewsPageBySmallCategoryIdSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double result = Math.ceil(newsService.findBySmallCategorySize(enumSmallCategoryId) / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<News>> findAllNewsPageBySearch(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam("name") String name,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        enumNameSearch = name;
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findByNamePage(name, pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size search
    @GetMapping("/search/size")
    public ResponseEntity<Double> findAllNewsPageBySearchSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double result = Math.ceil(newsService.findByNamePageSize(enumNameSearch) / 10);
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<News>> findAllNewsPageByTag(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam("tag-id") int tagId,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        enumTagId = tagId;
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findByTag(tagId, pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size tag
    @GetMapping("/tag/size")
    public ResponseEntity<Double> findAllNewsPageByTagSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double result = Math.ceil(newsService.findByTagSize(enumTagId) / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/time")
    public ResponseEntity<List<News>> findAllNewsPageByTime(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 10;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findNewsByTime(pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size time
    @GetMapping("/time/size")
    public ResponseEntity<Double> findAllNewsPageByTime(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("news").getSize();
        double result = Math.ceil(pageNumber / 10);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<News>> findAllNewsPageByHot(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (page < 1) page = 1;
        if (size < 0) size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        List<News> newsList = newsService.findNewsByLike(pageable);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //size hot
    @GetMapping("/hot/size")
    public ResponseEntity<Double> findAllNewsPageByHotSize(
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        double pageNumber = recordService.findByName("news").getSize();
        double result = Math.ceil(pageNumber / 5);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<List<News>> findAllNewsByUserId(
            @RequestParam("user-id") int id,
            HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<News> newsList = newsService.findAllNewsByUserId(id);
        if (newsList != null) return new ResponseEntity<>(newsList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
