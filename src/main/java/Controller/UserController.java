package event_management.Controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import event_management.Model.User;
import event_management.Service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }

        String inputEmail = user.getEmail().trim().toLowerCase();
        List<User> existingUsers = userService.getAllUsers();
        for (User u : existingUsers) {
            if (u.getEmail() != null && u.getEmail().trim().equalsIgnoreCase(inputEmail)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This email is already registered. Please log in instead.");
            }
        }

        try {
            user.setEmail(inputEmail);
            User saved = userService.saveUser(user);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("This email is already registered. Please log in instead.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration error: " + e.getMessage());
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        String inputEmail = user.getEmail().trim().toLowerCase();
        String inputPassword = user.getPassword().trim();

        List<User> users = userService.getAllUsers();

        for (User existingUser : users) {
            if (existingUser.getEmail() != null
                    && existingUser.getEmail().trim().equalsIgnoreCase(inputEmail)
                    && existingUser.getPassword() != null
                    && existingUser.getPassword().equals(inputPassword)) {

                return ResponseEntity.ok(existingUser);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
    }
}