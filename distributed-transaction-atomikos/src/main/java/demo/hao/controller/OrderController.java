package demo.hao.controller;


import demo.hao.model.ds1.Inventory;
import demo.hao.model.ds2.Orders;
import demo.hao.service.InventoryService;
import demo.hao.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    Iterable<Orders> getOrders() {
        return ordersService.getOrders();
    }

    @GetMapping("/inventory-status")
    Optional<Inventory> getInventoryStatus() {
        return inventoryService.getInventoryStatus("Test inventory");
    }

    @Transactional
    @PostMapping
    Orders createOrder(@RequestBody Orders orders) {
        return ordersService.createOrder(orders, "Test inventory");
    }

}

