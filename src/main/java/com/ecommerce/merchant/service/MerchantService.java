package com.ecommerce.merchant.service;

import com.ecommerce.merchant.entity.Merchant;

import java.util.List;

public interface MerchantService {
    public Merchant save(Merchant merchant);
    public Merchant findOne(String merchantId);
    public  Merchant update(Merchant merchant);
    public  void delete(String merchantId);
    public List<Merchant> findAll();
}
