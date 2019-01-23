package com.ecommerce.merchant.DTO.algorithm;

import com.ecommerce.merchant.entity.Merchant;

public class GroupByMerchantId {
    private String merchantId;
    private int productSold;
    private double merchantRating;

    public GroupByMerchantId(String merchantId, long productSold, double merchantRating) {
        this.merchantId = merchantId;
        this.productSold = (int) productSold;
        this.merchantRating = merchantRating;
    }

    public String getMerchantId() { return merchantId; }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getProductSold() {
        return productSold;
    }

    public void setProductSold(int productSold) {
        this.productSold = productSold;
    }

    public double getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(double merchantRating) {
        this.merchantRating = merchantRating;
    }
}
