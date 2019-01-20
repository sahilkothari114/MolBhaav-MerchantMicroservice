package com.ecommerce.merchant.service;

import com.ecommerce.merchant.entity.ProductMerchant;
import com.ecommerce.merchant.repository.ProductMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductMerchantServiceImpl implements ProductMerchantService {
    @Autowired
    ProductMerchantRepository productMerchantRepository;

    @Override
    public ProductMerchant save(ProductMerchant productMerchant) {
        return productMerchantRepository.save(productMerchant);
    }

    @Override
    public ProductMerchant findOne(String productMerchant) {
        return productMerchantRepository.findOne(productMerchant);
    }

    @Override
    public ProductMerchant update(ProductMerchant productMerchant) {
        return productMerchantRepository.save(productMerchant);
    }

    @Override
    public void delete(String productMerchantId) {
        productMerchantRepository.delete(productMerchantId);
    }

    @Override
    public List<ProductMerchant> findAll() {
        Iterable<ProductMerchant> productMerchantIterable = productMerchantRepository.findAll();
        List<ProductMerchant> productMerchantList = new ArrayList<>();
        productMerchantIterable.forEach(productMerchantList::add);
        return productMerchantList;
    }

    @Override
    public List<ProductMerchant> findByProductId(String productId) {
        return productMerchantRepository.findByProductId(productId);

    }

    @Override
    public int findCountByProductId(String productId) {
        return productMerchantRepository.countByProductId(productId);
    }

    @Override
    public double maxPriceByProductId(String productId) {
        return productMerchantRepository.maxPriceByProductId(productId);
    }

    @Override
    public double minPriceByProductId(String productId) {
        return productMerchantRepository.minPriceByProductId(productId);
    }
}
