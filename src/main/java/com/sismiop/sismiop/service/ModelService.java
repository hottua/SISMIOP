package com.sismiop.sismiop.service;

import org.springframework.context.annotation.Bean;


public interface ModelService <T, U>{
    boolean add(T t);
    boolean addAll(Iterable<T> ts);
    boolean update(T t);
    boolean delete(U u);
    T getById(U u);

}
