package com.backend.storehouseservice.mapper;

import com.backend.pojo.pojo.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Products> {
    @Select("SELECT id from products where code = #{code}")
    public Integer selectIdByCode(String code);

    @Select("SELECT * from products;")
    public List<Products> selectAll();
}
