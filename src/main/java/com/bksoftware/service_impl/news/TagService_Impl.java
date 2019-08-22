package com.bksoftware.service_impl.news;

import com.bksoftware.entities.news.News;
import com.bksoftware.entities.news.Tag;
import com.bksoftware.repository.news.NewsRepository;
import com.bksoftware.repository.news.TagRepository;
import com.bksoftware.service.news.TagService;
import com.bksoftware.service_impl.category.MenuService_Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TagService_Impl implements TagService {

    private final static Logger LOGGER = Logger.getLogger(TagService_Impl.class.getName());

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<Tag> findAll() {
        try {
            return tagRepository.findByStatus(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-tag-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Tag> findAllPage(Pageable pageable) {
        try {
            return tagRepository.findAllPage(pageable).getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-tag-page-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Tag> findByNews(int newsId) {
        try {
            News news = newsRepository.findById(newsId);
            return news.getTags()
                    .stream()
                    .filter(Tag::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-tag-by-news-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Tag findById(int id) {
        try {
            Tag tag = tagRepository.findById(id);
            if (tag.isStatus()) return tag;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tag-by-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Tag> findByName(String name) {
        try {
            List<Tag> tags = tagRepository.findByNameTag(name);
            return tags;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-tag-by-name-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveTag(Tag tag) {
        try {
            tagRepository.save(tag);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-tag-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteTag(int id) {
        try {

            Tag tag = findById(id);
            tag.setStatus(false);
            tagRepository.save(tag);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-tag-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public Tag findByNameUnique(String name) {
        try {
            Tag tag = tagRepository.findByName(name);
            return tag;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-tag-by-name-error : {0}", ex.getMessage());
        }
        return null;
    }
}
