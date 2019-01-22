package com.ecommerce.merchant.controller;

import com.ecommerce.merchant.DTO.*;
import com.ecommerce.merchant.entity.Merchant;
import com.ecommerce.merchant.entity.ProductMerchant;
import com.ecommerce.merchant.repository.ProductMerchantRepository;
import com.ecommerce.merchant.service.MerchantService;
import com.ecommerce.merchant.service.ProductMerchantService;
import com.ecommerce.merchant.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productMerchants")
public class ProductMerchantController {

    @Autowired
    ProductMerchantService productMerchantService;

    @Autowired
    MerchantService merchantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);

    @RequestMapping(value = "/getByProductId/{productId}", method = RequestMethod.GET)
    public ProductMerchantEmbeddedDTO getByProductId(@PathVariable("productId") String productId){
        LOGGER.info("Received GET request for getByProductId:" +productId);
        ProductMerchantEmbeddedDTO productMerchantEmbeddedDTO = new ProductMerchantEmbeddedDTO();
        //fetching product details from the product microservice
        RestTemplate restTemplate = new RestTemplate();
        ProductDTO productDTO = restTemplate.getForObject(Constant.PRODUCT_MICROSERVICE_BASE_URL+"/products/get/"+productId, ProductDTO.class);
        BeanUtils.copyProperties(productDTO,productMerchantEmbeddedDTO);
        for (ProductMerchant productMerchant : productMerchantService.findByProductId(productId)) {
            MerchantPriceQuantityDTO merchantPriceQuantityDTO = new MerchantPriceQuantityDTO();
            merchantPriceQuantityDTO.setEmailId(productMerchant.getMerchant().getEmailId());
            merchantPriceQuantityDTO.setName(productMerchant.getMerchant().getName());
            merchantPriceQuantityDTO.setPrice(productMerchant.getPrice());
            merchantPriceQuantityDTO.setQuantity(productMerchant.getQuantity());
            merchantPriceQuantityDTO.setMerchantId(productMerchant.getMerchant().getMerchantId());
            productMerchantEmbeddedDTO.add(merchantPriceQuantityDTO);
        }
        return productMerchantEmbeddedDTO;
    }

    @RequestMapping(value = "/getByProductList", method = RequestMethod.POST)
    public List<ProductMerchantDTO> getByProductList(@RequestBody List<ProductDTO> productDTOList){
        LOGGER.info("Received POST request for getByProductId:" );
        List<ProductMerchantDTO> productMerchantDTOList = new ArrayList<>();
        for (ProductDTO productDTO: productDTOList) {
            for (ProductMerchant productMerchant : productMerchantService.findByProductId(productDTO.getProductId())) {
                ProductMerchantDTO productMerchantDTO = new ProductMerchantDTO();
                BeanUtils.copyProperties(productMerchant,productMerchantDTO);
                productMerchantDTOList.add(productMerchantDTO);
            }
        }
        return productMerchantDTOList;
    }

    @RequestMapping(value = "/getCountByProductList", method = RequestMethod.POST)
    public List<ProductDTO> getCountByProductList(@RequestBody List<ProductDTO> productDTOList){
        LOGGER.info("Received POST request for getCountByProductId:" );
        List<ProductDTO> productDTOListWithCount = new ArrayList<>();
        for (ProductDTO productDTO:productDTOList) {
            ProductDTO productDTO1 = productDTO;
            productDTO1.setMerchantCount(productMerchantService.findCountByProductId(productDTO.getProductId()));
            productDTO1.setLowestPrice(productMerchantService.minPriceByProductId(productDTO.getProductId()));
            productDTO1.setHighestPrice(productMerchantService.maxPriceByProductId(productDTO.getProductId()));
            productDTOListWithCount.add(productDTO1);
        }
        return productDTOListWithCount;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addProductMerchant(@RequestBody  ProductMerchantDTO productMerchantDTO){
        ProductMerchant productMerchant = new ProductMerchant();
        BeanUtils.copyProperties(productMerchantDTO, productMerchant);
        Merchant merchant = merchantService.findOne(productMerchantDTO.getMerchant().getMerchantId());
        productMerchant.setMerchant(merchant);
        ProductMerchant productMerchant1= productMerchantService.save(productMerchant);
        return new ResponseEntity<String>(productMerchant1.getProductMerchantId(),HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reduceQuantity", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> reduceQuantity(@RequestBody Map<String,Integer> productList){
        for (String string:productList.keySet()) {
            int quantity = productMerchantService.findOne(string).getQuantity();
            ProductMerchant productMerchant= productMerchantService.findOne(string);
            productMerchant.setQuantity(productMerchant.getQuantity()-productList.get(string));
            productMerchantService.save(productMerchant);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

}
