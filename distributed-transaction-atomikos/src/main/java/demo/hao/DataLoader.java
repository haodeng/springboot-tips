package demo.hao;

import demo.hao.dao.ds1.InventoryRepository;
import demo.hao.model.ds1.Inventory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class DataLoader {
    private final InventoryRepository inventoryRepository;

    public DataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    private void loadData() {
        // Init inventory
        inventoryRepository.save(new Inventory("Test inventory"));
    }
}


