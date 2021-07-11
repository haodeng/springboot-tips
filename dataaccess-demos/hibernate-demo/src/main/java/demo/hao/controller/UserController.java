package demo.hao.controller;


import demo.hao.dto.UserDto;
import demo.hao.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    

    @GetMapping
    public List<UserDto> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return this.userService.findById(id);
    }

    @GetMapping("/active")
    public List<UserDto> findAllActive() {
        return this.userService.findAllActive();
    }

    @GetMapping("/inactive")
    public List<UserDto> findAllInactive() {
        return this.userService.findAllInactive();
    }

    @PostMapping
    public UserDto create(UserDto userDto) {
        return this.userService.create(userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.userService.delete(id);
    }
}
