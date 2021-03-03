package com.warehouseforgoods.warehouseforgoodsbackend.Utills;

import com.warehouseforgoods.warehouseforgoodsbackend.Model.Product;

public class ObjectMapper {
    public static void map(Product sourceProduct,Product targetProduct){
        if(targetProduct.getId()==null){
            targetProduct.setId(sourceProduct.getId());
        }
        if(targetProduct.getPrice()==null){
            targetProduct.setPrice(sourceProduct.getPrice());
        }
        if(targetProduct.getDescription()==null){
            targetProduct.setDescription(sourceProduct.getDescription());
        }
        if(targetProduct.getImgUrl()==null){
            targetProduct.setImgUrl(sourceProduct.getImgUrl());
        }
        if(targetProduct.getDateAdded()==null){
            targetProduct.setDateAdded(sourceProduct.getDateAdded());
        }
    }
}
