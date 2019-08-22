package com.bksoftware.service_impl.user;

import com.bksoftware.entities.json_payload.RegisterForm;
import com.bksoftware.entities.news.News;
import com.bksoftware.entities.user.AppRole;
import com.bksoftware.entities.user.AppUser;
import com.bksoftware.repository.news.NewsRepository;
import com.bksoftware.repository.user.AppRoleRepository;
import com.bksoftware.repository.user.AppUserRepository;
import com.bksoftware.service.user.AppUserService;
import com.bksoftware.service_impl.news.NewsService_Impl;
import com.bksoftware.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AppUserService_Impl implements AppUserService {

    private final static Logger LOGGER = Logger.getLogger(AppUserService_Impl.class.getName());

    private final AppUserRepository appUserRepository;

    private final AppRoleRepository appRoleRepository;

    private final NewsRepository newsRepository;

    public AppUserService_Impl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, NewsRepository newsRepository) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public AppUser findByEmail(String email) {
        try {
            return appUserRepository.findByEmail(email);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-user-by-email-error : {0}", ex.getCause());
        }
        return null;
    }

    @Override
    public AppUser findByEmailAndPassword(String email, String password) {
        try {
            return appUserRepository.findByEmailAndPassword(email, MD5.encode(password));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-user-by-email-and-password-error : {0}", ex.getCause());
        }
        return null;
    }

    @Override
    public AppUser findById(int id) {
        try {
            return appUserRepository.findById(id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-user-by-id-error : {0}", ex.getCause());
        }
        return null;
    }

    @Override
    public List<AppUser> findAll() {
        try {
            return appUserRepository.findAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-user-error : {0}", ex.getCause());
        }
        return new ArrayList<>();
    }

    @Override

    public boolean saveAppUser(AppUser appUser) {
        try {
            appUserRepository.save(appUser);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-user-error : {0}", ex.getCause());
            return false;
        }
    }

    @Override
    public boolean saveAppUser(RegisterForm registerForm) {

        List<AppRole> appRoles = new ArrayList<>();
        appRoles.add(appRoleRepository.findByName("ROLE_USER"));
        AppUser appUser = new AppUser();
        appUser.setEmail(registerForm.getUsername());
        appUser.setPassword(MD5.encode(registerForm.getPassword()));
        appUser.setName(registerForm.getName());
        appUser.setGender(registerForm.getGender());
        appUser.setAddress(registerForm.getAddress());
        appUser.setBirthDay(registerForm.getBirthday());
        appUser.setPhoneNumber(registerForm.getPhoneNumber());
        appUser.setJob(registerForm.getJob());
        appUser.setJoinDate(LocalDate.now());
        appUser.setAppRoles(appRoles);
        appUser.setStatus(true);
        try {
            appUserRepository.save(appUser);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-user-error : {0}", ex.getCause());
            return false;
        }

    }

    @Override
    public boolean deleteAppUser(AppUser appUser) {
        try {
            appUser.setStatus(false);
            appUserRepository.save(appUser);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-user-error : {0}", ex.getCause());
            return false;
        }
    }

    @Override
    public AppUser findByNews(int newsId) {
        News news = newsRepository.findById(newsId);
        try {
            return news.getAppUser();
        }catch (Exception ex){
            LOGGER.log(Level.SEVERE, "find-user-error : {0}", ex.getCause());
        }
        return null;
    }

    @Override
    public List<AppUser> findAllPage(Pageable pageable) {
        try {
            Page<AppUser> userPage = appUserRepository.findAllPage(pageable);
            List<AppUser> users = userPage.getContent();
            users.get(1).setStatus(false); // pop admin
            return users.stream()
                    .filter(AppUser::isStatus)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-user-page-error : {0}", ex.getMessage());
        }
        return null;
    }
}
