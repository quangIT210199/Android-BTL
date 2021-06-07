package com.example.nguyenvanquang_b17dcat148.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    private String imagePath;

    public ProductImage() {
    }

    public ProductImage(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
