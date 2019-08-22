package com.bksoftware.controller.viewer.news;

import com.bksoftware.entities.news.LikeNews;
import com.bksoftware.entities.news.News;
import com.bksoftware.service.news.LikeNewsService;
import com.bksoftware.service.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/public/like")
public class LikeNewsController {

    @Autowired
    private LikeNewsService likeNewsService;

    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<Object> findLikeByUserAndNews(
            @RequestParam("id-user") int idUser,
            @RequestParam("id-news") int idNews,
            HttpServletResponse response

    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ((idNews + "").isEmpty() || (idUser + "").isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        News news = newsService.findById(idNews);
        boolean result = likeNewsService.findLikeNew(idNews, idUser);
        if (result) {
            return new ResponseEntity<>("1", HttpStatus.OK);
        }
        return new ResponseEntity<>("0",HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveLikeByUserAndNews(
            @RequestParam("id-user") int idUser,
            @RequestParam("id-news") int idNews,
            HttpServletResponse response

    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        News news = newsService.findById(idNews);
        news.setLike(news.getLike() + 1);
        newsService.saveNews(news);
        LikeNews likeNews = new LikeNews();
        likeNews.setIdNews(idNews);
        likeNews.setIdUser(idUser);
        likeNewsService.saveLikeNews(likeNews);
        return new ResponseEntity<>("1", HttpStatus.OK);

    }
}
