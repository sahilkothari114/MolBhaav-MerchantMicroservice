package com.ecommerce.merchant.DTO.order;

import com.ecommerce.merchant.DTO.StaticAttributeDTO;

import java.util.List;

public class ProductDTO {
    private String productId;
    private String productName;
    private String productImageUrl;
    private String productDescription;
    private List<StaticAttributeDTO> staticAttributeList;
    private CategoryDTO category;
    private String productUsp;

    private double price;
    private String merchantId;
    private int quantity;

    public ProductDTO() {
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

    public List<StaticAttributeDTO> getStaticAttributeList() {
        return staticAttributeList;
    }

    public void setStaticAttributeList(List<StaticAttributeDTO> staticAttributeList) {
        this.staticAttributeList = staticAttributeList;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getProductUsp() {
        return productUsp;
    }

    public void setProductUsp(String productUsp) {
        this.productUsp = productUsp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
