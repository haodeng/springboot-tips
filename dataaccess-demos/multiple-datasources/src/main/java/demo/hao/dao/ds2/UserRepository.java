package demo.hao.dao.ds2;

import demo.hao.model.ds2.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
