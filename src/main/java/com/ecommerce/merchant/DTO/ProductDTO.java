package com.ecommerce.merchant.DTO;

import java.util.Collection;
public class ProductDTO {
    private String productId;
    private String productName;
    private String categoryId;
    private String productImageUrl;
    private String productUsp;
    private String productDescription;
    private Collection<StaticAttributeDTO> staticAttributeList;
    private int merchantCount;
    private double lowestPrice;
    private double highestPrice;

    public double getLowestPrice() {
        return lowestPrice;
    }
    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
    public double getHighestPrice() {
        return highestPrice;
    }
    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }
    public int getMerchantCount() {
        return merchantCount;
    }
    public void setMerchantCount(int merchantCount) {
        this.merchantCount = merchantCount;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getProductImageUrl() {
        return productImageUrl;
    }
    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public Collection<StaticAttributeDTO> getStaticAttributeList() {
        return staticAttributeList;
    }
    public void setStaticAttributeList(Collection<StaticAttributeDTO> staticAttributeList) {
        this.staticAttributeList = staticAttributeList;
    }
    public String getProductUsp() {
        return productUsp;
    }
    public void setProductUsp(String productUsp) {
        this.productUsp = productUsp;
    }
}