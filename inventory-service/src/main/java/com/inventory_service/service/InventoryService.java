package com.inventory_service.service;

import com.inventory_service.dto.InventoryResponse;
import com.inventory_service.model.Inventory;
import com.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Wait started.....");
        Thread.sleep(10000);
        log.info("Wait ended");

        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inv ->
                InventoryResponse.builder()
                        .skuCode(inv.getSkuCode())
                        .isInStock(inv.getQuantity() > 0).build()
        ).toList();
    }
}
