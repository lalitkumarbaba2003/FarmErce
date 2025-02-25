package com.example.farmerce.models;

import java.io.Serializable;

public class MyOrdersModel implements Serializable {

    String productName;
    String productPrice;
    String totalQuantity;
    int totalPrice;
    String img_url;
    String productQuantity;
    String documentId;
    String productStatus;

    public MyOrdersModel() {
    }

    public MyOrdersModel(String productName, String productPrice, String totalQuantity, int totalPrice, String img_url, String productQuantity, String documentId, String productStatus) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.img_url = img_url;
        this.productQuantity = productQuantity;
        this.documentId = documentId;
        this.productStatus = productStatus;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
