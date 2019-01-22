package com.ecommerce.merchant.DTO.algorithm;

public class GroupByProductId {
    private String productId;
    private double price;
    private double minPrice;
    private double maxPrice;

    public GroupByProductId(String productId, double price, double minPrice, double maxPrice) {
        this.productId = productId;
        this.price = price;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {

        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
