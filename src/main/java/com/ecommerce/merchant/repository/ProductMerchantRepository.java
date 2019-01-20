package com.ecommerce.merchant.repository;

import com.ecommerce.merchant.entity.ProductMerchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMerchantRepository extends CrudRepository<ProductMerchant,String> {
    public List<ProductMerchant> findByProductId(String productId);

    public int countByProductId(String productId);

    @Query("SELECT MIN(pm.price) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public double minPriceByProductId(String productId);

    @Query("SELECT MAX(pm.price) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public double maxPriceByProductId(String productId);

}
