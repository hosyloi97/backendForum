package com.bksoftware.repository.news;

import com.bksoftware.entities.news.News;
import com.bksoftware.entities.user.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    List<News> findByStatus(boolean status);

    @Query("SELECT n FROM News n WHERE n.smallCategory.id = :id and  n.status= true and n.newsStatus= true")
    Page<News> findBySmallCategoryId(@Param("id") int id, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.smallCategory.id = :id and  n.status= true and n.newsStatus= true ")
    List<News> findBySmallCategoryIdSize(@Param("id") int id);

    @Query("SELECT n FROM News n WHERE n.smallCategory.bigCategory.id = :id and n.status= true and n.newsStatus= true")
    Page<News> findByBigCategoryId(@Param("id") int id, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.smallCategory.bigCategory.id = :id and n.status= true and n.newsStatus= true")
    List<News> findByBigCategoryIdSize(@Param("id") int id);

    @Query("SELECT n FROM News n WHERE n.smallCategory.bigCategory.menu.id = :id and n.status= true and n.newsStatus= true")
    Page<News> findByMenuId(@Param("id") int id, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.smallCategory.bigCategory.menu.id = :id and n.status= true and n.newsStatus= true")
    List<News> findByMenuIdSize(@Param("id") int id);

    @Query("SELECT n FROM News n WHERE n.title LIKE CONCAT('%',:title,'%') and n.status= true and n.newsStatus= true")
    List<News> findByNameSize(@Param("title") String name);

    @Query("SELECT n FROM News n WHERE n.appUser.name LIKE CONCAT('%',:name,'%') and n.status= true and n.newsStatus= true")
    Page<News> findByUserName(@Param("name") String name, Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.status= true and n.newsStatus= false ")
    List<News> findByNewsStatus(boolean status);

    //===================================================

    List<News> findByAppUserAndStatus(AppUser appUser, boolean status);

    @Query("select n from News n where n.status=true and n.newsStatus= true order by n.id desc")
    Page<News> findAllPage(Pageable pageable);

    @Query("SELECT n FROM News n WHERE n.title LIKE CONCAT('%',:title,'%') and n.status= true and n.newsStatus= true")
    Page<News> findByNamePage(@Param("title") String title, Pageable pageable);

    News findById(int id);

    @Query(value = " SELECT n from News n  where n.status=true and n.newsStatus= true order by (n.time) desc ")
    Page<News> findNewsByTime(Pageable pageable);

    @Query(value = " SELECT n from News n  where n.status=true and n.newsStatus= true order by (n.like) desc ")
    Page<News> findNewsByLike(Pageable pageable);

    @Query(value = " SELECT n from News n  where n.status=true and n.newsStatus= true order by (n.like) desc ")
    Page<News> findNewsByLikeWithMonth(Pageable pageable);
}
