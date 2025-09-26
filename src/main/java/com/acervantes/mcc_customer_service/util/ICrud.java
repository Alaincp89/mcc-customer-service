package com.acervantes.mcc_customer_service.util;

import java.util.List;

public interface ICrud<T> {
    List<T> getAll();
    T add(T t);
    T update(T t);
    T delete(String cu);
    T getById(String id);
}
