package pl.library.domain.user.repository;

import pl.library.adapters.mysql.model.user.User;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User loginUser(String email, String password) throws UserNotFoundException;
    User registerUser(User user) throws UserExistsException;
    User updateUserProfile(Long id, User user) throws UserNotFoundException, UserExistsException;
    User addFavoriteBookToUser(Long user_id, Long book_id) throws UserNotFoundException;
    void subtractFavoriteBookFromUser(Long user_id, Long book_id) throws UserNotFoundException;
}
