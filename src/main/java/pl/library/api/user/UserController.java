package pl.library.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.user.User;
import pl.library.adapters.mysql.model.user.UserRole;
import pl.library.domain.user.UserServiceImpl;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

/**    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getSingleUserById(@PathVariable long id) throws UserNotFoundException {
        return userService.getById(id);
    }*/

    @GetMapping("search/email")
    @ResponseStatus(HttpStatus.OK)
    public User getSingleUserByEmail(@RequestParam String email) throws UserNotFoundException {
        return userService.getByEmail(email);
    }

/**    @GetMapping("/search/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() throws UserNotFoundException {
        return userService.getAll();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsersByFirstNameOrLastName(@RequestParam String firstName,
                                                       @RequestParam String lastName) throws UserNotFoundException {
        return userService.getByFirstNameOrLastName(firstName, lastName);
    }*/

    @GetMapping("/role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsersByRole(@PathVariable UserRole role) throws UserNotFoundException {
        return userService.getByRole(role);
    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmailAndPassword(@RequestBody User user) throws UserNotFoundException {
        return userService.login(user.getEmail(), user.getPassword());
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) throws UserExistsException {
        return userService.registry(user);
    }

    @PutMapping("/sign-in/profile/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUser(@RequestParam Long id,
                           @Valid @RequestBody User user) throws UserNotFoundException, UserExistsException {
        return userService.update(id, user);
    }

/**    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam Long id) throws UserNotFoundException {
        userService.delete(id);
    }*/
}
