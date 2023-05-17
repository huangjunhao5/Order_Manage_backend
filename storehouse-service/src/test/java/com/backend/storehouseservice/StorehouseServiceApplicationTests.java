package com.backend.storehouseservice;

import com.backend.pojo.client.StorehouseClient;
import com.backend.pojo.pojo.Products;
import com.backend.pojo.pojo.StoreHouse;
import com.backend.storehouseservice.mapper.ProductMapper;
import com.backend.storehouseservice.mapper.StorehouseMapper;
import com.backend.storehouseservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StorehouseServiceApplicationTests {

//    @Autowired
//    private StorehouseClient storehouseClient;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StorehouseMapper storehouseMapper;
    @Test
    void testCreateNewProduct() {
        Products product = new Products();
        product.setName("大椅子");
        product.setCode("DYZ");
        productService.createNewProduct(product);
//        productMapper.insert(product);
    }

    @Test
    void tsetAddNum(){
        productService.changeStoreNum(6,6, 1);
//        StoreHouse storeHouse = new StoreHouse();
//        storeHouse.setNum(4);
//        storeHouse.setProductId(6);
//        storehouseMapper.updateById(storeHouse);
    }
//    @Test
//    void testClient(){
//        Result result = storehouseClient.getAllProduct();
//        System.out.println(result);
//    }

}
