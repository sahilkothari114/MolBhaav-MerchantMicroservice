package com.ecommerce.merchant.repository;

import com.ecommerce.merchant.entity.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant,String> {
}
