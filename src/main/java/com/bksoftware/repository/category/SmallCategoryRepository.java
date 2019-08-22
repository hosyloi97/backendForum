package com.bksoftware.repository.category;

import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.SmallCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmallCategoryRepository extends JpaRepository<SmallCategory, Integer> {

    @Query("SELECT sc FROM SmallCategory sc WHERE sc.bigCategory.id = :id")
    List<SmallCategory> findByBigCategoryId(@Param("id") int id);

    SmallCategory findById(int id);

    List<SmallCategory> findByStatus(boolean status);

    @Query("select sc from SmallCategory sc where sc.status=true ")
    Page<SmallCategory> findAllPage(Pageable pageable);


}
