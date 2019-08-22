package com.bksoftware.repository.news;

import com.bksoftware.entities.news.LikeNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LikeNewsRepository extends JpaRepository<LikeNews, Integer> {

    @Query("SELECT l FROM LikeNews l WHERE l.idNews = :idNews and l.idUser= :idUser")
    LikeNews findByNewsAndUser(@Param("idNews") int idNews, @Param("idUser") int idUser);
}
