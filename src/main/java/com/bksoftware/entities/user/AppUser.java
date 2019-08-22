package com.bksoftware.entities.user;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table(name = "user")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 32)
    private String password;

    private String name;

    private int gender;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "phone_number")
    private int phoneNumber;

    private String address;

    private String job;

    private String present;

    @Column(name = "avatar_url")
    private String avatarURL;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "post_count")
    private int postCount;

    @Column(name = "like_count")
    private int likeCount;

    private boolean status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_has_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> appRoles;

    public Collection<? extends GrantedAuthority> grantedAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        appRoles.forEach(role -> list.add(new SimpleGrantedAuthority(role.getName())));
       //list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return list;
    }

}
