package com.warehouseforgoods.warehouseforgoodsbackend.Error;

public class UserExceptions extends RuntimeException {
    private Error error;
    public UserExceptions(Error code){
        super(code.getMessage());
        this.error=code;
    }

    public Error getError() {
        return error;
    }

    public enum Error {
        USER_DAO_GET_FAILED("Haven't found any products with that id"),
        USER_DAO_GET_BY_EMAIL_FAILED("User with that email is already exist");

        private String message;

        Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

    }
}
