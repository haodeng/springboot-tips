package demo.hao.dao.ds1;

import demo.hao.model.ds1.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    Optional<Inventory> findByName(String name);
}
