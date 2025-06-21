package com.inventory_service.controller;

import com.inventory_service.dto.InventoryResponse;
import com.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;



    //api-  http://localhost:8082/api/v1/stock/iphone_13

    @GetMapping("/stock/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }


    //api-  http://localhost:8082/api/v1/stock?sku-code=iphone_13&sku-code=iphone_13_red
    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}


