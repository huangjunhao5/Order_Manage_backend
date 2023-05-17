package com.backend.storehouseservice.service;

import com.backend.pojo.pojo.ProductStore;
import com.backend.pojo.pojo.Products;

import java.util.List;

public interface ProductService {
//    public static Integer AddFlag = 0;
//    public static Integer SubFlag = 1;
    public boolean createNewProduct(Products product);
    public boolean addProductNum(Products product, Integer num);
    public boolean subProductNum(Products product, Integer num);
    public boolean deleteProduct(Products product);
    public ProductStore selectById(Integer id);
    public List<ProductStore> selectAll();
    public List<Products> selectAllProduct();
    public boolean updateProductInfo(Products product);
    public boolean changeStoreNum(Integer id, Integer num, Integer changeFlag);
}
