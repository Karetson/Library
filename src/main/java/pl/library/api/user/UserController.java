package pl.library.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.user.User;
import pl.library.api.user.dto.AddFavoriteResponse;
import pl.library.api.user.dto.CreateUserRequest;
import pl.library.api.user.dto.CreateUserResponse;
import pl.library.api.user.dto.GetUserResponse;
import pl.library.api.user.dto.LoginUserResponse;
import pl.library.api.user.dto.ProfileUserResponse;
import pl.library.api.user.dto.UpdateUserRequest;
import pl.library.domain.user.UserServiceImpl;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    // user registration
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse addUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws UserExistsException {
        User addedUser = userService.registerUser(createUserRequest.toUser());

        return new CreateUserResponse(addedUser.getId());
    }

    // searching for a user by id
    @GetMapping("/search/{id}")
    public ProfileUserResponse getUserById(@PathVariable Long id) throws UserNotFoundException {
        User gainedUser = userService.getUserById(id);

        return new ProfileUserResponse(gainedUser.getFirstName(),
                gainedUser.getLastName(),
                gainedUser.getEmail(),
                gainedUser.getCreatedAt(),
                gainedUser.getFavoriteBooks(),
                gainedUser.getBorrows());
    }

    // user login
    @GetMapping("/sign-in")
    public LoginUserResponse getUserByEmailAndPassword(@RequestParam String email,
                                                       @RequestParam String password) throws UserNotFoundException {
        User loggedUser = userService.loginUser(email, password);

        return new LoginUserResponse(loggedUser.getId(),
                loggedUser.getFirstName(),
                loggedUser.getFavoriteBooks(),
                loggedUser.getBorrows());
    }

    // searching for a user by email
    @GetMapping("search")
    public GetUserResponse getUserByEmail(@RequestParam String email) throws UserNotFoundException {
        User gainedUser = userService.getUserByEmail(email);

        return new GetUserResponse(gainedUser.getFirstName(),
                gainedUser.getLastName(),
                gainedUser.getEmail(),
                gainedUser.getFavoriteBooks(),
                gainedUser.getBorrows(),
                gainedUser.getCreatedAt());
    }

    // editing a user's profile
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse updateUser(@PathVariable Long id,
                                         @Valid @RequestBody UpdateUserRequest updateUserRequest) throws UserNotFoundException, UserExistsException {
        User updatedUser = userService.updateUserProfile(id, updateUserRequest.toUser());

        return new CreateUserResponse(updatedUser.getId());
    }

    // adding a user's favorite book
    @PutMapping("/favorite/add/{user_id}/{book_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddFavoriteResponse addFavoriteBookToUser(@PathVariable Long user_id,
                                                     @PathVariable Long book_id) throws UserNotFoundException {
        User updatedUser = userService.addFavoriteBookToUser(user_id, book_id);

        return new AddFavoriteResponse(updatedUser.getId(),
                updatedUser.getFavoriteBooks());
    }

    // deleting user favorite book
    @DeleteMapping("/favorite/subtract/{user_id}/{book_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subtractFavoriteBookFromUser(@PathVariable Long user_id,
                                             @PathVariable Long book_id) throws UserNotFoundException {
        userService.subtractFavoriteBookFromUser(user_id, book_id);
    }
}
