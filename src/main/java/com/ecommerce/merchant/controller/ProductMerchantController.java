package com.ecommerce.merchant.controller;

import com.ecommerce.merchant.DTO.*;
import com.ecommerce.merchant.DTO.order.ProductOrderDTO;
import com.ecommerce.merchant.DTO.order.RatingDTO;
import com.ecommerce.merchant.entity.Merchant;
import com.ecommerce.merchant.entity.ProductMerchant;
import com.ecommerce.merchant.service.MerchantService;
import com.ecommerce.merchant.service.ProductMerchantService;
import com.ecommerce.merchant.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/productMerchants")
public class ProductMerchantController {

    @Autowired
    ProductMerchantService productMerchantService;

    @Autowired
    MerchantService merchantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);
    @CrossOrigin("*")
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
            merchantPriceQuantityDTO.setRank(productMerchant.getRank());
            merchantPriceQuantityDTO.setRating(productMerchant.getRating());
            merchantPriceQuantityDTO.setRatingCount(productMerchant.getRatingCount());
            productMerchantEmbeddedDTO.add(merchantPriceQuantityDTO);
        }
        return productMerchantEmbeddedDTO;
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/getByProductList", method = RequestMethod.POST)
    public List<ProductMerchantDTO> getByProductList(@RequestBody List<ProductDTO> productDTOList){
        LOGGER.info("Received POST request for getByProductList" );
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
    @CrossOrigin("*")
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
    @CrossOrigin("*")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addProductMerchant(@RequestBody  ProductMerchantDTO productMerchantDTO){
        ProductMerchant productMerchant = new ProductMerchant();
        BeanUtils.copyProperties(productMerchantDTO, productMerchant);
        Merchant merchant = merchantService.findOne(productMerchantDTO.getMerchant().getMerchantId());
        productMerchant.setMerchant(merchant);
        ProductMerchant productMerchant1= productMerchantService.save(productMerchant);
        return new ResponseEntity<String>(productMerchant1.getProductMerchantId(),HttpStatus.CREATED);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/reduceQuantity", method = RequestMethod.PUT)
    public List<ProductOrderDTO> reduceQuantity(@RequestBody List<ProductOrderDTO> productList){
        List<ProductOrderDTO> productOrderDTOList = new ArrayList<>();
        for (ProductOrderDTO productOrderDTO:productList) {
            ProductMerchant productMerchant1 = productMerchantService.findOne(productOrderDTO.getProductId()+"-"+productOrderDTO.getMerchantId());
            if (productMerchant1.getQuantity()>productOrderDTO.getQuantity()){
                break;
            }
            productMerchant1.setQuantity(productMerchant1.getQuantity()-productOrderDTO.getQuantity());
            productMerchantService.save(productMerchant1);
            productOrderDTOList.add(productOrderDTO);
        }
        return productOrderDTOList;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/getByProductMerchantList", method = RequestMethod.POST)
    public List<ProductMerchantDTO> getByProducMerchanttList(@RequestBody List<ProductMerchantDTO> productMerchantDTOList){
        LOGGER.info("Received POST request for ProductMerchantDTO" );
        List<ProductMerchantDTO> productMerchantDTOList1 = new ArrayList<>();
        for (ProductMerchantDTO productMerchantDTO: productMerchantDTOList) {
            ProductMerchant productMerchant = productMerchantService.findByProductIdAndMerchantId(productMerchantDTO.getProductId(),productMerchantDTO.getMerchant().getMerchantId());
            ProductMerchantDTO productMerchantDTO1= new ProductMerchantDTO();
            Merchant merchant = productMerchant.getMerchant();
            MerchantDTO merchantDTO = new MerchantDTO();
            BeanUtils.copyProperties(productMerchant,productMerchantDTO1);
            BeanUtils.copyProperties(merchant,merchantDTO);
            productMerchantDTO1.setMerchant(merchantDTO);
            productMerchantDTOList1.add(productMerchantDTO1);
        }
        return productMerchantDTOList1;
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/addRating", method = RequestMethod.POST)
    public ResponseEntity<String> addrating(RatingDTO rating){
        ProductMerchant productMerchant = productMerchantService.findByProductIdAndMerchantId(rating.getProductId(),rating.getMerchantId());
        productMerchant.setRating(productMerchant.getRating()+rating.getRating());
        if (productMerchant.getRatingCount()==0){
            LOGGER.info("rating count is 0.");
        }
        productMerchant.setRatingCount(productMerchant.getRatingCount()+1);
        LOGGER.info("rating count is ."+productMerchant.getRatingCount());

        productMerchantService.save(productMerchant);
        return new ResponseEntity<String>(productMerchant.getProductMerchantId(),HttpStatus.CREATED);
    }
}
