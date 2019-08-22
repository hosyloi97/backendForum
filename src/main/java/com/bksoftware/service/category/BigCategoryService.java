package com.bksoftware.service.category;

import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BigCategoryService {

    BigCategory findById(int id);

    List<BigCategory> findByMenu(Menu menu);

    List<BigCategory> findAll();

    List<BigCategory> findAllPage(Pageable pageable);

    boolean saveBigCategory(BigCategory bigCategory);

    boolean deleteBigCategory(int id);

}
