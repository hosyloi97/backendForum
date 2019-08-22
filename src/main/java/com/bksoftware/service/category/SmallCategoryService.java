package com.bksoftware.service.category;

import com.bksoftware.entities.category.SmallCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SmallCategoryService {

    List<SmallCategory> findByBigCategoryId(int id);

    SmallCategory findById(int id);

    List<SmallCategory> findAll();

    List<SmallCategory> findAllPage(Pageable pageable);

    boolean saveSmallCategory(SmallCategory smallCategory);

    boolean deleteSmallCategory(int id);
}
