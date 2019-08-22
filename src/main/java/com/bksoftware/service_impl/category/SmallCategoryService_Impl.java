package com.bksoftware.service_impl.category;

import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.repository.category.SmallCategoryRepository;
import com.bksoftware.service.category.SmallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SmallCategoryService_Impl implements SmallCategoryService {

    private final static Logger LOGGER = Logger.getLogger(SmallCategoryService_Impl.class.getName());

    @Autowired
    private SmallCategoryRepository smallCategoryRepository;


    @Override
    public List<SmallCategory> findByBigCategoryId(int id) {
        try {
            List<SmallCategory> smallCategories = smallCategoryRepository.findByBigCategoryId(id);
            return smallCategories
                    .stream()
                    .filter(SmallCategory::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-small-category-by-big-category-id-error : {0}", ex.getMessage());
        }
        return null;
    }


    @Override
    public SmallCategory findById(int id) {
        try {
            SmallCategory smallCategory = smallCategoryRepository.findById(id);
            if (smallCategory.isStatus()) return smallCategory;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-small-category-by-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<SmallCategory> findAll() {
        try {
            return smallCategoryRepository.findByStatus(true);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-small-category-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<SmallCategory> findAllPage(Pageable pageable) {
        try {
            Page<SmallCategory> smallCategoryPage = smallCategoryRepository.findAllPage(pageable);
            return smallCategoryPage.getContent();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-small-category-page-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveSmallCategory(SmallCategory smallCategory) {
        try {
            smallCategoryRepository.save(smallCategory);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-small-category-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteSmallCategory(int id) {
        try {
            SmallCategory smallCategory = findById(id);
            smallCategory.setStatus(false);
            smallCategoryRepository.save(smallCategory);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-small-category-error : {0}", ex.getMessage());
        }
        return false;
    }
}
