package pl.library.domain.user.repository;

import pl.library.adapters.mysql.model.user.User;
import pl.library.adapters.mysql.model.user.UserRole;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User getById(Long id) throws UserNotFoundException;
    User getByEmail(String email) throws UserNotFoundException;
    List<User> getByRole(UserRole role) throws UserNotFoundException;
    User login(String email, String password) throws UserNotFoundException;
    User register(User user) throws UserExistsException;
    User editProfile(Long id, User user) throws UserNotFoundException, UserExistsException;
    User addFavorite(Long user_id, Long book_id) throws UserNotFoundException;
    void subtractFavorite(Long user_id, Long book_id) throws UserNotFoundException;
}
