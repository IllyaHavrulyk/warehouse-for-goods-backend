package com.warehouseforgoods.warehouseforgoodsbackend.Error;

public class ProductExceptions extends RuntimeException {
    private Error error;
    public ProductExceptions(ProductExceptions.Error code){
        super(code.getMessage());
        this.error=code;
    }

    public Error getError() {
        return error;
    }

    public enum Error {
        PRODUCT_DAO_LIST_FAILED("Can't show list of products"),
        PRODUCT_DAO_GET_FAILED("Haven't found any products with that id"),
        PRODUCT_DAO_UPDATE_FAILED( "Couldn't update product");


        private String message;

        Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

    }
}
