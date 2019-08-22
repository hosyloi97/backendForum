package com.bksoftware.controller.main.change;


import com.bksoftware.controller.main.admin.HomeAdminController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CreatePageController {

    public String getToken(HttpServletRequest request) {
        HomeAdminController homeAdminController = new HomeAdminController();
        String token = homeAdminController.getToken(request);
        return token;
    }

    //=========================Category=================================
    @GetMapping("/create-big-category")
    public String createBigCategoryPage(HttpServletRequest request) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "createBigCategory";
    }

    @GetMapping("/create-small-category")
    public String createSmallCategoryPage(HttpServletRequest request) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "createSmallCategory";
    }

    @GetMapping("/create-menu")
    public String createCategoryPage(HttpServletRequest request) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "createMenu";
    }

    @GetMapping("/update-big-category")
    public String updateBigCategoryPage(
            @RequestParam("id") int id,
            HttpServletRequest request
    ) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "updateBigCategory";
    }

    @GetMapping("/update-small-category")
    public String updateSmallCategoryPage(
            @RequestParam("id") int id,
            HttpServletRequest request
    ) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "updateSmallCategory";
    }

    @GetMapping("/update-menu")
    public String updateCategoryPage(
            @RequestParam("id") int id,
            HttpServletRequest request
    ) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "updateMenu";
    }

    @GetMapping("/create-tag")
    public String createTagPage(HttpServletRequest request) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "createTag";
    }

    @GetMapping("/update-tag")
    public String updateTagPage(
            @RequestParam("id") int id,
            HttpServletRequest request
    ) {
        String token = getToken(request);

        if (token == null) {
            return "login";
        }
        return "updateTag";
    }

}