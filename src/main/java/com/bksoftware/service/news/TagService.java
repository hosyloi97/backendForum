package com.bksoftware.service.news;

import com.bksoftware.entities.news.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    List<Tag> findAll();

    List<Tag> findAllPage(Pageable pageable);

    List<Tag> findByNews(int newsId);

    Tag findById(int id);

    List<Tag> findByName(String name);

    boolean saveTag(Tag tag);

    boolean deleteTag(int id);

    Tag findByNameUnique(String name);
}
