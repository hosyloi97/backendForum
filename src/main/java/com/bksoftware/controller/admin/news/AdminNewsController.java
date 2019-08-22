package com.bksoftware.controller.admin.news;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.news.News;
import com.bksoftware.service.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin/news")
public class AdminNewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/check")
    public ResponseEntity<Object> checkNewsStatusTrue(
            HttpServletResponse response,
            @RequestParam("id") int id
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        News news = newsService.findById(id);
        if (news != null) {
            news.setNewsStatus(true);
            newsService.saveNews(news);
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
