package pl.library.domain.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.user.User;
import pl.library.domain.service.user.UserService;
import pl.library.exception.UserExistsException;
import pl.library.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/sign-in/profile/{id}")
    public User getSingleUserById(@PathVariable long id) throws UserNotFoundException {
        return userService.getById(id);
    }

    @GetMapping("/user/email/{email}")
    public User getSingleUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return userService.getByEmail(email);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() throws UserNotFoundException {
        return userService.getAll();
    }

    @GetMapping("/user/name/{name}")
    public List<User> getAllUsersByFirstNameOrLastName(@PathVariable String firstName, String lastName) throws UserNotFoundException {
        return userService.getByFirstNameOrLastName(firstName, lastName);
    }

    @GetMapping("/user/role/{role}")
    public List<User> getAllUsersByRole(@PathVariable String role) throws UserNotFoundException {
        return userService.getByRole(role);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) throws UserExistsException {
        return userService.add(user);
    }

    @PutMapping("/sign-in/profile/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@PathVariable long id, @Valid @RequestBody User user) throws UserNotFoundException {
        return userService.update(id, user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) throws UserNotFoundException {
        userService.delete(id);
    }
}
