package com.bksoftware.repository.category;

import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigCategoryRepository extends JpaRepository<BigCategory, Integer> {


    List<BigCategory> findByMenu(Menu menu);

    BigCategory findById(int id);

    List<BigCategory> findByStatus(boolean status);

    @Query("select bc from BigCategory bc where bc.status=true ")
    Page<BigCategory> showBigCategory(Pageable pageable);
}
