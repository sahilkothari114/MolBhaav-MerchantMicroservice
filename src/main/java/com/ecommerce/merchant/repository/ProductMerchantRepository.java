package com.ecommerce.merchant.repository;

import com.ecommerce.merchant.DTO.algorithm.GroupByMerchantId;
import com.ecommerce.merchant.DTO.algorithm.GroupByProductId;
import com.ecommerce.merchant.entity.Merchant;
import com.ecommerce.merchant.entity.ProductMerchant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMerchantRepository extends CrudRepository<ProductMerchant,String> {

    public List<ProductMerchant> findByProductId(String productId);

    public ProductMerchant findByProductIdAndMerchant_MerchantId(String productId, String merchantId);

    public int countByProductId(String productId);

    @Query("SELECT MIN(pm.price) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public double minPriceByProductId(String productId);

    @Query("SELECT MAX(pm.price) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public double maxPriceByProductId(String productId);

//    @Query("SELECT pm.productId, SUM(pm.price), MIN(pm.price), MAX(pm.price) FROM ProductMerchant pm GROUP BY pm.productId")
//    public List<GroupByProductId> groupByProductId();

    @Query("SELECT NEW com.ecommerce.merchant.DTO.algorithm.GroupByMerchantId(" +
            "pm.merchant.merchantId, COUNT(pm.productId), AVG(pm.rating))" +
            " FROM ProductMerchant pm GROUP BY pm.merchant.merchantId")
    public List<GroupByMerchantId> groupingByMerchant();

    //@Query("SELECT pm.* FROM ProductMerchant pm where pm.merchant.merchantId=?1")
    public List<ProductMerchant> findByMerchant_MerchantId(String merchantId);

    @Modifying
    @Query("UPDATE ProductMerchant pm SET pm.rank = ?1 WHERE pm.productMerchantId = ?2")
    public void generateRank(double rank, String productMerchantId);

    @Query("SELECT MIN(pm.quantity) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public int minStockByProductId(String productId);

    @Query("SELECT MAX(pm.quantity) FROM ProductMerchant pm WHERE pm.productId = ?1")
    public int maxStockByProductId(String productId);
}
