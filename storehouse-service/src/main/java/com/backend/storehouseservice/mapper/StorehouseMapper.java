package com.backend.storehouseservice.mapper;

import com.backend.pojo.pojo.StoreHouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorehouseMapper extends BaseMapper<StoreHouse> {
    @Select("select productid, num from storehouse where productid = #{id};")
    public StoreHouse selectById(Integer id);
    @Update("update storehouse set num = #{num} where productid = #{productId};")
    public int updateById(StoreHouse storeHouse);
}
