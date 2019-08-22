package com.bksoftware.service_impl.news;


import com.bksoftware.entities.news.News;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.entities.user.AppUser;
import com.bksoftware.repository.news.NewsRepository;
import com.bksoftware.repository.news.TagRepository;
import com.bksoftware.repository.user.AppUserRepository;
import com.bksoftware.service.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class NewsService_Impl implements NewsService {

    private final static Logger LOGGER = Logger.getLogger(NewsService_Impl.class.getName());

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public List<News> findAll() {
        try {
            return newsRepository.findByStatus(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-status-flase-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<News> findAllNewsStatusFalse() {
        try {
            return newsRepository.findByNewsStatus(false);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-tag-error : {0}", ex.getMessage());
        }
        return null;
    }


    @Override
    public List<News> findByTag(int tagId, Pageable pageable) {
        try {
            Tag tag = tagRepository.findById(tagId);
            return tag.getNews()
                    .stream()
                    .filter(News::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-tag-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Float findByTagSize(int tagId) {
        Tag tag = tagRepository.findById(tagId);
        return (float) tag.getNews()
                .stream()
                .filter(News::isStatus)
                .collect(Collectors.toList()).size();
    }

    @Override
    public List<News> findBySmallCategory(int smallCategoryId, Pageable pageable) {
        try {
            return newsRepository.findBySmallCategoryId(smallCategoryId, pageable).getContent();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-small-category-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Float findBySmallCategorySize(int smallCategoryId) {
        return (float) newsRepository.findBySmallCategoryIdSize(smallCategoryId).size();
    }

    @Override
    public List<News> findByMenu(int menuId, Pageable pageable) {
        try {
            return newsRepository.findByMenuId(menuId, pageable).getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-menu-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Float findByMenuSize(int menuId) {
        return (float) newsRepository.findByMenuIdSize(menuId).size();
    }

    @Override
    public List<News> findByBigCategory(int bigCategoryId, Pageable pageable) {
        try {
            return newsRepository.findByBigCategoryId(bigCategoryId, pageable).getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-big-category-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Float findByBigCategorySize(int bigCategoryId) {
        return (float) newsRepository.findByBigCategoryIdSize(bigCategoryId).size();
    }

    @Override
    public List<News> findSizeNewsAndTagByBigCategory(int bigCategoryId) {
        return newsRepository.findByBigCategoryIdSize(bigCategoryId);
    }

    @Override
    public List<News> findAllPage(Pageable pageable) {
        try {
            Page<News> newsPage = newsRepository.findAllPage(pageable);
            return newsPage.getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-page-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<News> findByNamePage(String title, Pageable pageable) {
        try {
            Page<News> newsPage = newsRepository.findByNamePage(title, pageable);

            if (newsPage.isEmpty()) {
                newsPage = newsRepository.findByUserName(title, pageable);
            }
            return newsPage.getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-name-page-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Float findByNamePageSize(String title) {
        return (float) newsRepository.findByNameSize(title).size();
    }

    @Override
    public News findById(int id) {
        try {
            News news = newsRepository.findById(id);
            if (news.isStatus()) return news;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<News> findNewsByTime(Pageable pageable) {
        try {
            return newsRepository.findNewsByTime(pageable).getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-time-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<News> findNewsByLike(Pageable pageable) {
        try {
            return newsRepository.findNewsByLike(pageable).getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-like-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<News> findNewsByLikeWithMonth(Pageable pageable) {
        try {
            List<News> newsList = newsRepository.findNewsByLikeWithMonth(pageable).getContent();
            List<News> newsLikeByMonth = new ArrayList<>();
            newsList.forEach(news -> {
                if (news.getTime().getMonth().getValue() == LocalDate.now().getMonth().getValue()
                ) {
                    newsLikeByMonth.add(news);
                }
            });
            return newsLikeByMonth;

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-like-with-month-error : {0}", ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now().getMonth().getValue());
    }

    @Override
    public boolean saveNews(News news) {
        try {
            newsRepository.save(news);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-news-error : {0}", ex.getMessage());
        }
        return false;
    }


    @Override
    public boolean deleteNews(int id) {
        try {
            News news = findById(id);
            news.setStatus(false);
            newsRepository.save(news);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-news-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public List<News> findAllNewsByUserId(int id) {
        try {
            AppUser appUser = appUserRepository.findById(id);
            List<News> newsList = newsRepository.findByAppUserAndStatus(appUser, true);
            return newsList;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-news-by-user-id-error : {0}", ex.getMessage());
        }
        return null;
    }

}
