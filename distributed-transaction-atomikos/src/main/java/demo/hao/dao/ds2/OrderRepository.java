package demo.hao.dao.ds2;

import demo.hao.model.ds2.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Long> {}
