package com.bksoftware.entities.news;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "like_news")
@Data
public class LikeNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_news")
    private int idNews;

    @Column(name = "id_user")
    private int idUser;

    public LikeNews() {
    }
}
