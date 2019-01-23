package com.ecommerce.merchant.service;

import com.ecommerce.merchant.DTO.algorithm.GroupByMerchantId;
import com.ecommerce.merchant.DTO.algorithm.MerchantOrders;
import com.ecommerce.merchant.entity.ProductMerchant;
import com.ecommerce.merchant.repository.ProductMerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    @Override
    //@Scheduled(fixedRate = 60*60*1000, initialDelay = 5000)
    public void calculateMerchantRank() {
        List<GroupByMerchantId> groupByMerchantIds = productMerchantRepository.groupingByMerchant();
        //List<GroupByProductId> groupByProductIds = productMerchantRepository.groupByProductId();

        //API CALL
        List<MerchantOrders> merchantOrdersList = new ArrayList<>();
        for (GroupByMerchantId groupByMerchantId : groupByMerchantIds) {
            MerchantOrders merchantOrders = new MerchantOrders();
            merchantOrders.setMerchantId(groupByMerchantId.getMerchant().getMerchantId());
            merchantOrdersList.add(merchantOrders);
        }

        //TODO:PUT URL IN
        final String uri = "  ";

        //MAKING CALL
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity requestEntity = new HttpEntity(merchantOrdersList, headers);
        ResponseEntity<?> entityResponse = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, List.class);

        //PARSING RESULTS
        List ordersMadeList = (List) entityResponse.getBody();
        Iterator iterator = ordersMadeList.iterator();
//        List<MerchantOrders> ordersMade = new ArrayList<>();
        HashMap<String, Integer> ordersMade = new HashMap<>();
        while (iterator.hasNext()) {
            MerchantOrders orderMade = mapper.convertValue(iterator.next(), MerchantOrders.class);
//            ordersMade.add(orderMade);
            ordersMade.put(orderMade.getMerchantId(), orderMade.getOrdersMade());
        }

        //Stock Normalizing Prep
        int minOrder = Collections.min(ordersMade.values());
        int maxOrder = Collections.max(ordersMade.values());

        for (GroupByMerchantId groupByMerchantId : groupByMerchantIds) {
            List<ProductMerchant> pmlist = productMerchantRepository.findByMerchant(groupByMerchantId.getMerchant());
            for (ProductMerchant productMerchant : pmlist) {

                //Normalize Prices
                double maxPrice = productMerchantRepository.maxPriceByProductId(productMerchant.getProductId());
                double minPrice = productMerchantRepository.minPriceByProductId(productMerchant.getProductId());
                double normalizedPrice = (productMerchant.getPrice() - minPrice) / (maxPrice - minPrice);

                //Normalize Orders
                int ordersMadeToMerchant = ordersMade.get(groupByMerchantId.getMerchant().getMerchantId());
                double normalizedOrdersMadeToMerchant = (ordersMadeToMerchant - minOrder) / (maxOrder - minOrder);

                //Normalize Stock
                int totalProductsByMerchant = groupByMerchantId.getProductSold();
                int currentStock = productMerchant.getQuantity();

                int minStock = productMerchantRepository.minStockByProductId(productMerchant.getProductId());
                int maxStock = productMerchantRepository.maxStockByProductId(productMerchant.getProductId());
                double normalizedStock = (productMerchant.getQuantity() - minStock) / (maxStock - minStock);

                double merchantRating = groupByMerchantId.getMerchantRating();
                double productRating = productMerchant.getRating();

                //generate rank
                double[] weights = {1, 1, 1, 1, 1, 1};
                double rank = weights[0] * normalizedPrice
                        + weights[1] * normalizedOrdersMadeToMerchant
                        + weights[2] * totalProductsByMerchant
                        + weights[3] * normalizedStock
                        + weights[4] * merchantRating
                        + weights[5] * productRating;

                productMerchantRepository.generateRank(rank, productMerchant.getProductMerchantId());
            }
        }
    }

    @Override
    public ProductMerchant findByProductIdAndMerchantId(String productId, String merchantId) {
        return productMerchantRepository.findByProductIdAndMerchant_MerchantId(productId,merchantId);
    }
//    @Scheduled(fixedRate = 60*60*1000, initialDelay = 5000)
//    public void test(){
//        List<GroupByMerchantId> groupByMerchantIdList = productMerchantRepository.groupingByMerchant();
//        for(GroupByMerchantId groupByMerchantId : groupByMerchantIdList){
//            System.out.println(groupByMerchantId.getMerchant() + " " + groupByMerchantId.getMerchantRating());
//        }
//    }
}
