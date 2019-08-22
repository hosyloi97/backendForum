package com.bksoftware.entities.news;

import com.bksoftware.entities.category.SmallCategory;
import com.bksoftware.entities.user.AppUser;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "news")
@Data
@SecondaryTables({
        @SecondaryTable(name = "small_category"),
        @SecondaryTable(name = "tag"),
        @SecondaryTable(name = "user")
})
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String image;

    @NotNull
    @Column(name = "news_status")
    private boolean newsStatus;

    @NotNull
    private LocalDateTime time;

    @NotNull
    @Column(name = "number_like")
    private int like;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "small_category_id", nullable = false)
    @NotNull
    private SmallCategory smallCategory;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private AppUser appUser;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "news_has_tag",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @NotNull
    private boolean status;

}
