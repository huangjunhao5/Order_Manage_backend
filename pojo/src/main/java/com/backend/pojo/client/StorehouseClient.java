package com.backend.pojo.client;

//import com.backend.pojo.pojo.ProductStore;
import com.backend.pojo.pojo.ProductStore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("storehouse-service")
public interface StorehouseClient {
    @GetMapping("/storehouse")
    public Result getAllProduct();
    @PutMapping("/storehouse/{id}")
    public Result ChangeStoreNum(@PathVariable Integer id, @RequestParam Integer num, @RequestParam Integer changeFlag);
    @GetMapping("/storehouse/{id}")
    public Result<ProductStore> getById(@PathVariable Integer id);
}
