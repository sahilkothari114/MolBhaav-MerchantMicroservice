package com.ecommerce.merchant.controller;

import com.ecommerce.merchant.DTO.MerchantDTO;
import com.ecommerce.merchant.entity.Merchant;
import com.ecommerce.merchant.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/merchants")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MerchantController.class);
    @CrossOrigin("*")
    @RequestMapping(value = "/get/{merchantId}",method = RequestMethod.GET)
    public MerchantDTO getMerchant(@PathVariable("merchantId") String merchantId){
        LOGGER.info("Received a GET request for MerchantId:" +merchantId);
        Merchant merchant = merchantService.findOne(merchantId);
        MerchantDTO merchantDTO = new MerchantDTO();
        BeanUtils.copyProperties(merchant,merchantDTO);
        return merchantDTO;
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<MerchantDTO> addMerchant(@RequestBody MerchantDTO merchantDTO){
        LOGGER.info("Received a POST request for addMerchant");
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO,merchant);
        merchant = merchantService.save(merchant);
        BeanUtils.copyProperties(merchant,merchantDTO);
        return new ResponseEntity<MerchantDTO>(merchantDTO,HttpStatus.CREATED);
    }
    @CrossOrigin("*")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<MerchantDTO> getAllMerchant(){
        LOGGER.info("Received a GET request for GetAllMerchant");
        List<MerchantDTO> merchantDTOList = new ArrayList<>();
        for (Merchant merchant: merchantService.findAll()) {
            MerchantDTO merchantDto = new MerchantDTO();
            BeanUtils.copyProperties(merchant,merchantDto);
            merchantDTOList.add(merchantDto);
        }
        return merchantDTOList;
    }
}
