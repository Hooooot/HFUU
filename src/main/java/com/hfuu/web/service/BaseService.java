package com.hfuu.web.service;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


/**
 * @author 浅忆
 */
@Service
public interface BaseService<T> {
    void insert(T e);
    void delete(T e);
    void update(T e);
    T findById(Serializable id);
    boolean isExist(Serializable id);
    List findAll();
    Long count();
    List findByHql(String hql);
    List findByHql(String hql, Object... param);
}
