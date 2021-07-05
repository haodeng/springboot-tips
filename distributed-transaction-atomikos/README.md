# Use atomikos

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jta-atomikos</artifactId>
        </dependency>

Config jta transaction manager and multiple datasource

Check JtaTransactionManagerConfig, DataSource1Config and DataSource2Config
      
Add to Transactional to OrdersService.createOrder

    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(Orders order, String inventoryName) {
              
## Demo happy scenario

    curl http://localhost:8080/orders/inventory-status
    {"id":1,"name":"Test inventory","quantities":100}
    
    curl http://localhost:8080/orders
    []
    
    # Place an order
    curl -X POST -d '{"name":"test order"}' -H "Content-Type:application/json" http://localhost:8080/orders
    # Inventory quntity decrease 1
    curl http://localhost:8080/orders/inventory-status
    {"id":1,"name":"Test inventory","quantities":99}
    # New order added
    curl http://localhost:8080/orders
    [{"id":1,"name":"test order"}]

order created in orders db, inventory decreased in inventory db



## Demo place order failed

Simulate an exception in OrderService

    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(Orders order, String inventoryName) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByName(inventoryName);
        Inventory inventory = inventoryOptional.orElseThrow(
                () -> new IllegalArgumentException("Unable to find inventory " + inventoryName));

        inventory.setQuantities(inventory.getQuantities() - 1);
        inventoryRepository.save(inventory);

        Orders myOrder = orderRepository.save(order);
        if (order.getName().equals("failed")) {
            // Simulate a exception
            throw new RuntimeException("Simulate exception ignore pls");
        }

        return myOrder;
    }

and

    # Place an order failed
    curl -X POST -d '{"name":"failed"}' -H "Content-Type:application/json" http://localhost:8080/orders
    {"timestamp":"2021-07-04T23:27:38.662+00:00","status":500,"error":"Internal Server Error","path":"/orders"}
    
    # Inventory quntity do not change
    curl http://localhost:8080/orders/inventory-status
    {"id":1,"name":"Test inventory","quantities":99}
    
    # No new order added, still 1
    curl http://localhost:8080/orders
    [{"id":1,"name":"test order"}]
    
So 2 datasources are all rollback.