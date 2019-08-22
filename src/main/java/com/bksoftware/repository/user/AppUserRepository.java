package com.bksoftware.repository.user;

import com.bksoftware.entities.user.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    AppUser findByEmail(String email);

    AppUser findById(int id);

    AppUser findByEmailAndPassword(String email, String password);

    List<AppUser> findAll();

    List<AppUser> findByStatus(boolean status);

    @Query("select u from AppUser u where u.status=true ")
    Page<AppUser> findAllPage(Pageable pageable);

    Page<AppUser> findByName(String name, Pageable pageable);

}
