package com.backend.storehouseservice.service.Impl;

import com.backend.pojo.pojo.ProductStore;
import com.backend.pojo.pojo.Products;
import com.backend.pojo.pojo.StoreHouse;
import com.backend.storehouseservice.mapper.ProductMapper;
import com.backend.storehouseservice.mapper.ProductStoreMapper;
import com.backend.storehouseservice.mapper.StorehouseMapper;
import com.backend.storehouseservice.service.ProductService;
import io.seata.spring.annotation.GlobalTransactional;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final StorehouseMapper storehouseMapper;
    private final ProductStoreMapper productStoreMapper;

    public ProductServiceImpl(ProductMapper productMapper, StorehouseMapper storehouseMapper, ProductStoreMapper productStoreMapper) {
        this.productMapper = productMapper;
        this.storehouseMapper = storehouseMapper;
        this.productStoreMapper = productStoreMapper;
    }

    @Override
    public boolean createNewProduct(Products product) {
        int flag = productMapper.insert(product);
        if(flag == 0){
            return false;
        }
        Integer id = product.getId();// productMapper.selectIdByCode(product.getCode());
        StoreHouse storeHouse = new StoreHouse();
        storeHouse.setNum(0);
        storeHouse.setProductId(id);
        storehouseMapper.insert(storeHouse);
        return true;
    }

    private Integer getProductId(Products product){
        Integer id = product.getId();
        if(id == null){
            String code = product.getCode();
            id = productMapper.selectIdByCode(code);
        }
        return id;
    }

    @Override
    public boolean addProductNum(Products product, Integer num) {
        Integer id = getProductId(product);
        if(id == null){
            return false;
        }
        StoreHouse storeHouse = storehouseMapper.selectById(id);
        Integer old = storeHouse.getNum();
        storeHouse.setNum(old + num);
        storehouseMapper.updateById(storeHouse);
        return true;
    }

    @Override
    public boolean subProductNum(Products product, Integer num) {
        Integer id = getProductId(product);
        if(id == null){
            return false;
        }
        StoreHouse storeHouse = storehouseMapper.selectById(id);
        Integer old = storeHouse.getNum();
        if(old < num){
            return false;
        }
        storeHouse.setNum(old - num);
        storehouseMapper.updateById(storeHouse);
        return true;
    }

    @Override
    public boolean deleteProduct(Products product) {
        Integer id = getProductId(product);
        if(id == null){
            return false;
        }
        storehouseMapper.deleteById(id);
        productMapper.deleteById(id);
        return true;
    }

    @Override
    public ProductStore selectById(Integer id) {
        return productStoreMapper.selectById(id);
    }

    @Override
    public List<ProductStore> selectAll() {
        return productStoreMapper.selectAll();
    }

    @Override
    public List<Products> selectAllProduct() {
        return productMapper.selectAll();
    }

    @Override
    public boolean updateProductInfo(Products product) {
        int flag = productMapper.updateById(product);
        return flag == 1;
    }

    @GlobalTransactional
    @Override
    public boolean changeStoreNum(Integer id, Integer num, Integer changeFlag) {
        Products product = new Products();
        product.setId(id);
        if(Objects.equals(changeFlag, StoreHouse.Add)){
            return addProductNum(product,num);
        }else{
            return subProductNum(product, num);
        }
    }
}
