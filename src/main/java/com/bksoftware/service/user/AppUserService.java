package com.bksoftware.service.user;

import com.bksoftware.entities.json_payload.RegisterForm;
import com.bksoftware.entities.user.AppUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppUserService {

    AppUser findByEmail(String email);

    AppUser findByEmailAndPassword(String email, String password);

    AppUser findById(int id);

    List<AppUser> findAll();

    boolean saveAppUser(AppUser appUser);

    boolean saveAppUser(RegisterForm registerForm);

    boolean deleteAppUser(AppUser appUser);

    AppUser findByNews(int newsId);

    List<AppUser> findAllPage(Pageable pageable);
}
