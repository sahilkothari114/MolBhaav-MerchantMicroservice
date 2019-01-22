package com.ecommerce.merchant.DTO.algorithm;

import com.ecommerce.merchant.entity.Merchant;

public class GroupByMerchantId {
    private Merchant merchant;
    private int productSold;
    private double merchantRating;

    public GroupByMerchantId(Merchant merchant, int productSold, double merchantRating) {
        this.merchant = merchant;
        this.productSold = productSold;
        this.merchantRating = merchantRating;
    }

    public Merchant getMerchant() {

        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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
