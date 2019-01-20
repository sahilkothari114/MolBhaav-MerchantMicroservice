package com.ecommerce.merchant.service;

import com.ecommerce.merchant.entity.ProductMerchant;

import java.util.List;

public interface ProductMerchantService {
    public ProductMerchant save(ProductMerchant productMerchant);
    public ProductMerchant findOne(String productMerchant);
    public ProductMerchant update(ProductMerchant productMerchant);
    public void delete(String productMerchantId);
    public List<ProductMerchant> findAll();
    public List<ProductMerchant> findByProductId(String productId);
    public int findCountByProductId(String productId);
    public double maxPriceByProductId(String productId);
    public double minPriceByProductId(String productId);
}
