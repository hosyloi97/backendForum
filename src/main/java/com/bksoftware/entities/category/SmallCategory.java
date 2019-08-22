package com.bksoftware.entities.category;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "small_category")
@SecondaryTables({
        @SecondaryTable(name = "big_category")
})
public class SmallCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "big_category_id",nullable = false)
    @NotNull
    private BigCategory bigCategory;

    @NotNull
    private boolean status;

    public SmallCategory(){}
}
