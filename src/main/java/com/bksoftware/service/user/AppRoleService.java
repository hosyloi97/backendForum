package com.bksoftware.service.user;

import com.bksoftware.entities.user.AppRole;

import java.util.List;

public interface AppRoleService {

    List<AppRole> findAll();

    AppRole findByName(String name);
}
