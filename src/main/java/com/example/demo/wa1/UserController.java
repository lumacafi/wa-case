package com.example.demo.wa1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController()
@RequestMapping(value = "/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") final Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(user);
    }

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") final Integer id,
                                        @RequestBody User updatedUser) {

        Optional<User> storedUser = userRepository.findById(id);
        if (storedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            User user = storedUser.get();
            user.setName(updatedUser.getName());
            user.setDoc(updatedUser.getDoc());
            user.setUpdatedTimestamp(LocalDateTime.now());
            return ResponseEntity.ok(userRepository.save(user));
        }
    }


}
