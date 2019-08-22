package com.bksoftware.service.news;

import com.bksoftware.entities.news.LikeNews;

public interface LikeNewsService {

    boolean findLikeNew(int idNews, int idUser);

    boolean saveLikeNews(LikeNews likeNews);

}
