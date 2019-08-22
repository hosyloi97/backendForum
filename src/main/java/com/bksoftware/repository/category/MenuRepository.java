package com.bksoftware.repository.category;

import com.bksoftware.entities.category.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findById(int id);

    List<Menu> findByStatus(boolean status);
}
