package com.ecommerce.merchant.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductMerchantEmbeddedDTO {
    private String productId;
    private String productName;
    private String categoryId;
    private String productImageUrl;
    private String productUsp;
    private String productDescription;
    private Collection<StaticAttributeDTO> staticAttributeList;
    private List<MerchantPriceQuantityDTO> merchantDTOList = new ArrayList<MerchantPriceQuantityDTO>();

    public boolean add(MerchantPriceQuantityDTO merchantPriceQuantityDTO) {
        return merchantDTOList.add(merchantPriceQuantityDTO);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductUsp() {
        return productUsp;
    }

    public void setProductUsp(String productUsp) {
        this.productUsp = productUsp;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Collection<StaticAttributeDTO> getStaticAttributeList() {
        return staticAttributeList;
    }

    public void setStaticAttributeList(Collection<StaticAttributeDTO> staticAttributeList) {
        this.staticAttributeList = staticAttributeList;
    }

    public List<MerchantPriceQuantityDTO> getMerchantDTOList() {
        return merchantDTOList;
    }

    public void setMerchantDTOList(List<MerchantPriceQuantityDTO> merchantDTOList) {
        this.merchantDTOList = merchantDTOList;
    }

    public ProductMerchantEmbeddedDTO() {
    }

}