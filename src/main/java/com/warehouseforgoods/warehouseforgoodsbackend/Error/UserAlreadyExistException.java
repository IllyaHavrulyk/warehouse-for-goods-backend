package com.warehouseforgoods.warehouseforgoodsbackend.Error;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
