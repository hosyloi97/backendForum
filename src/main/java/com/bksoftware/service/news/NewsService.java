package com.bksoftware.service.news;


import com.bksoftware.entities.news.News;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface NewsService {

    List<News> findAllNewsStatusFalse();

    List<News> findAll();

    List<News> findByTag(int tagId, Pageable pageable);

    Float findByTagSize(int tagId);

    List<News> findBySmallCategory(int smallCategoryId, Pageable pageable);

    Float findBySmallCategorySize(int smallCategoryId);

    List<News> findByMenu(int menuId, Pageable pageable);

    Float findByMenuSize(int menuId);

    List<News> findByBigCategory(int bigCategoryId, Pageable pageable);

    Float findByBigCategorySize(int bigCategoryId);

    List<News> findSizeNewsAndTagByBigCategory(int bigCategoryId);

    List<News> findAllPage(Pageable pageable);

    List<News> findByNamePage(String title, Pageable pageable);

    Float findByNamePageSize(String title);

    News findById(int id);

    List<News> findNewsByTime(Pageable pageable);

    List<News> findNewsByLike(Pageable pageable);

    List<News> findNewsByLikeWithMonth(Pageable pageable);

    boolean saveNews(News news);

    boolean deleteNews(int id);

    List<News> findAllNewsByUserId(int id);
}
