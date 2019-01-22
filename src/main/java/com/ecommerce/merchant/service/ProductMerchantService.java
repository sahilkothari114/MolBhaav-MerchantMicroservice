package com.ecommerce.merchant.service;

import com.ecommerce.merchant.entity.ProductMerchant;

import java.util.List;

public interface ProductMerchantService {
    ProductMerchant save(ProductMerchant productMerchant);
    ProductMerchant findOne(String productMerchant);
    ProductMerchant update(ProductMerchant productMerchant);
    void delete(String productMerchantId);
    List<ProductMerchant> findAll();
    List<ProductMerchant> findByProductId(String productId);
    int findCountByProductId(String productId);
    double maxPriceByProductId(String productId);
    double minPriceByProductId(String productId);
    void calculateMerchantRank();
}
