package com.example.nguyenvanquang_b17dcat148.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("alias")
    private String alias;

    @SerializedName("fullDescription")
    private String fullDescription;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("price")
    private float price;

    @SerializedName("discountPercent")
    private float discountPercent;

    @SerializedName("mainImage")
    private String mainImage;

    @SerializedName("qrCodeImage")
    private String qrCodeImage;

    private String mainImagePath;

    // for test
    private int imgResouce;

    public int getImgResouce() {
        return imgResouce;
    }

    public void setImgResouce(int imgResouce) {
        this.imgResouce = imgResouce;
    }
    // for test

    public Product() {
    }

    public Product(Integer id, String name, String alias, String fullDescription, String shortDescription, float price, float discountPercent, String mainImage, String qrCodeImage, String mainImagePath) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.price = price;
        this.discountPercent = discountPercent;
        this.mainImage = mainImage;
        this.qrCodeImage = qrCodeImage;
        this.mainImagePath = mainImagePath;
    }

    public Product(String name, String shortDescription, int imgResouce) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.imgResouce = imgResouce;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", price=" + price +
                ", discountPercent=" + discountPercent +
                ", mainImage='" + mainImage + '\'' +
                ", qrCodeImage='" + qrCodeImage + '\'' +
                ", mainImagePath='" + mainImagePath + '\'' +
                '}';
    }
}
