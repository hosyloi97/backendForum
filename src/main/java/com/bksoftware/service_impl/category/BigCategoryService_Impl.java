package com.bksoftware.service_impl.category;

import com.bksoftware.entities.category.BigCategory;
import com.bksoftware.entities.category.Menu;
import com.bksoftware.repository.category.BigCategoryRepository;
import com.bksoftware.service.category.BigCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class BigCategoryService_Impl implements BigCategoryService {

    private final static Logger LOGGER = Logger.getLogger(BigCategoryService_Impl.class.getName());

    @Autowired
    private BigCategoryRepository bigCategoryRepository;

    @Override
    public BigCategory findById(int id) {
        try {
            BigCategory bigCategory = bigCategoryRepository.findById(id);
            if (bigCategory.isStatus()) return bigCategory;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-big-category-by-id-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<BigCategory> findByMenu(Menu menu) {
        try {
            List<BigCategory> bigCategories = bigCategoryRepository.findByMenu(menu);
            return bigCategories.stream()
                    .filter(BigCategory::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-big-category-by-menu-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<BigCategory> findAll() {
        try {
            return bigCategoryRepository.findByStatus(true)
                    .stream()
                    .filter(BigCategory::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-big-category-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public List<BigCategory> findAllPage(Pageable pageable) {
        try {
            Page<BigCategory> bigCategoryPage = bigCategoryRepository.findAll(pageable);
            return bigCategoryPage.getContent()
                    .stream()
                    .filter(BigCategory::isStatus)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-list-big-category-page-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveBigCategory(BigCategory bigCategory) {
        try {
            bigCategoryRepository.save(bigCategory);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-big-category-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteBigCategory(int id) {
        try {
            BigCategory bigCategory = findById(id);
            bigCategory.setStatus(false);
            bigCategoryRepository.save(bigCategory);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-big-category-error : {0}", ex.getMessage());
        }
        return false;
    }
}
