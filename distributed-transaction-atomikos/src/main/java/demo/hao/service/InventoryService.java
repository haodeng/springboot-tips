package demo.hao.service;

import demo.hao.dao.ds1.InventoryRepository;
import demo.hao.model.ds1.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Optional<Inventory> getInventoryStatus(String inventoryName) {
        return inventoryRepository.findByName(inventoryName);
    }

    public Inventory update(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}
