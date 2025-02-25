package com.example.farmerce.models;

import java.io.Serializable;

public class RecommendedProductModel implements Serializable {

    String name;
    String description;
    String img_url;
    String price;
    String type;

    public RecommendedProductModel() {
    }

    public RecommendedProductModel(String name, String description, String img_url, String price) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
    }

    public RecommendedProductModel(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
