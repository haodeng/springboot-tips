package demo.hao.controller;


import demo.hao.dao.ds2.UserRepository;
import demo.hao.model.ds2.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    User UserUser(@RequestBody User User) {
        return userRepository.save(User);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> putUser(@PathVariable Long id,
                                 @RequestBody User user) {

        return (userRepository.existsById(id))
                ? new ResponseEntity<>(userRepository.save(user), HttpStatus.OK)
                : new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}

