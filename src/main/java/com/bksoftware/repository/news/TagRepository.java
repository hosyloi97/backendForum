package com.bksoftware.repository.news;

import com.bksoftware.entities.news.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    List<Tag> findByStatus(boolean status);

    @Query("select t from Tag t where t.status=true ")
    Page<Tag> findAllPage(Pageable pageable);

    Tag findById(int id);


    @Query("SELECT t FROM Tag t WHERE t.name LIKE CONCAT('%',:name,'%') and t.status= true")
    List<Tag> findByNameTag(@Param("name") String name);

    Tag findByName(String name);
}
