package com.bksoftware.service_impl.news;

import com.bksoftware.entities.news.LikeNews;
import com.bksoftware.repository.news.LikeNewsRepository;
import com.bksoftware.service.news.LikeNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LikeNewsService_Impl implements LikeNewsService {

    private final static Logger LOGGER = Logger.getLogger(LikeNewsService_Impl.class.getName());

    @Autowired
    private LikeNewsRepository likeNewsRepository;

    @Override
    public boolean findLikeNew(int idNews, int idUser) {
        try {
            LikeNews likeNews = likeNewsRepository.findByNewsAndUser(idNews, idUser);
            if (likeNews == null) return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-like-by-user-and-news-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean saveLikeNews(LikeNews likeNews) {
        try {
            likeNewsRepository.save(likeNews);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-like-error : {0}", ex.getMessage());
        }
        return false;
    }
}
