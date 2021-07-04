package demo.hao.service;

import demo.hao.dao.ds1.InventoryRepository;
import demo.hao.dao.ds2.OrderRepository;
import demo.hao.model.ds1.Inventory;
import demo.hao.model.ds2.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public Iterable<Orders> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(Orders order, String inventoryName) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByName(inventoryName);
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            inventory.setQuantities(inventory.getQuantities() - 1);

            inventoryRepository.save(inventory);
        }

        Orders myOrder = orderRepository.save(order);
        if (order.getName().equals("failed")) {
            // Simulate a exception
            int i = 1/0;
        }

        return myOrder;
    }

}
