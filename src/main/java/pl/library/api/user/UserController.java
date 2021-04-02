package pl.library.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.library.adapters.mysql.model.user.User;
import pl.library.adapters.mysql.model.user.UserRole;
import pl.library.api.user.dto.AddUserRequest;
import pl.library.api.user.dto.AddUserResponse;
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

     @GetMapping("/search")
     @ResponseStatus(HttpStatus.OK)
     public User getSingleUserById(@RequestParam Long id) throws UserNotFoundException {
        return userService.getById(id);
     }

    @GetMapping("search/email")
    @ResponseStatus(HttpStatus.OK)
    public User getSingleUserByEmail(@RequestParam String email) throws UserNotFoundException {
        return userService.getByEmail(email);
    }

//     @GetMapping("/search/all")
//     @ResponseStatus(HttpStatus.OK)
//     public List<User> getAllUsers() throws UserNotFoundException {
//     return userService.getAll();
//     }
//
//     @GetMapping("/search")
//     @ResponseStatus(HttpStatus.OK)
//     public List<User> getAllUsersByFirstNameOrLastName(@RequestParam String firstName,
//                                                        @RequestParam String lastName) throws UserNotFoundException {
//        return userService.getByFirstNameOrLastName(firstName, lastName);
//     }

    @GetMapping("/role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsersByRole(@PathVariable UserRole role) throws UserNotFoundException {
        return userService.getByRole(role);
    }

    @GetMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmailAndPassword(@RequestParam String email,
                                          @RequestParam String password) throws UserNotFoundException {
        return userService.login(email, password);
    }

    //dto
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AddUserResponse addUser(@Valid @RequestBody AddUserRequest addUserRequest) throws UserExistsException {
         User addedUser = userService.register(addUserRequest.toUser());
         return new AddUserResponse(addedUser.getId());
    }

    @PutMapping("/sign-in/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public User editUser(@RequestParam Long id,
                           @Valid @RequestBody User user) throws UserNotFoundException, UserExistsException {
        return userService.editProfile(id, user);
    }

    @PutMapping("/favorite/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addFavorite(@RequestParam Long user_id,
                            @RequestParam Long book_id) throws UserNotFoundException {
        return userService.addFavorite(user_id, book_id);
    }

    @DeleteMapping("/favorite/subtract")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subtractFavorite(@RequestParam Long user_id,
                                 @RequestParam Long book_id) throws UserNotFoundException {
        userService.subtractFavorite(user_id, book_id);
    }

//    @DeleteMapping("/delete")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@RequestParam Long id) throws UserNotFoundException {
//        userService.delete(id);
//    }
}
