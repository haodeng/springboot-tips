package demo.hao.service;

import demo.hao.dao.UserRepository;
import demo.hao.dto.UserDto;
import demo.hao.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;

    public static UserDto mapToDto(User user) {
        if (user != null) {
            return new UserDto(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getTelephone(),
                    AddressService.mapToDto(user.getAddress())
            );
        }
        return null;
    }

    public UserDto create(UserDto userDto) {
        log.debug("Request to create User : {}", userDto);
        return mapToDto(
                this.userRepository.save(
                        new User(
                                userDto.getFirstName(),
                                userDto.getLastName(),
                                userDto.getEmail(),
                                userDto.getTelephone(),
                                Boolean.TRUE,
                                AddressService.createFromDto(userDto.getAddress())
                        )
                )
        );
    }

    public List<UserDto> findAll() {
        log.debug("Request to get all Users");
        return this.userRepository.findAll()
                .stream()
                .map(UserService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        log.debug("Request to get User : {}", id);
        return this.userRepository.findById(id).map(UserService::mapToDto).orElse(null);
    }

    public List<UserDto> findAllActive() {
        log.debug("Request to get all Users");
        return this.userRepository.findAllByEnabled(true)
                .stream()
                .map(UserService::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> findAllInactive() {
        log.debug("Request to get all Users");
        return this.userRepository.findAllByEnabled(false)
                .stream()
                .map(UserService::mapToDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);

        User customer = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Cannot find User with id " + id));

        customer.setEnabled(false);
        this.userRepository.save(customer);
    }
}
