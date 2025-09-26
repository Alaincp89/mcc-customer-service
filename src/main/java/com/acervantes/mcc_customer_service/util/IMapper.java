package com.acervantes.mcc_customer_service.util;

public interface IMapper<T> {

    T getDTO();
    void setData(T t);
}
