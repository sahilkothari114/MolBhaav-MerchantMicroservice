package com.ecommerce.merchant.service;

import com.ecommerce.merchant.entity.Merchant;
import com.ecommerce.merchant.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;
    @Override
    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public Merchant findOne(String merchantId) {
        return merchantRepository.findOne(merchantId);
    }

    @Override
    public Merchant update(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public void delete(String merchantId) {
        merchantRepository.delete(merchantId);
    }

    @Override
    public List<Merchant> findAll() {
        List<Merchant> merchantList= new ArrayList<>();
        Iterable<Merchant> merchantIterable = merchantRepository.findAll();
        merchantIterable.forEach(merchantList::add);
        return merchantList;    }
}
