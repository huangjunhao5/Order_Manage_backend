package com.backend.storehouseservice.mapper;

import com.backend.pojo.pojo.ProductStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductStoreMapper {
    public List<ProductStore> selectAll();
    public ProductStore selectById(Integer id);
}
