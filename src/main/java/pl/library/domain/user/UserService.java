package pl.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.user.User;
import pl.library.adapters.mysql.model.user.UserRole;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User with '" + id + "' ID not found!"));
    }

    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("User with '" + email + "' email not found!"));
    }

    public List<User> getAll() throws UserNotFoundException {
        if (userRepository.findAll().size() > 0) {
            return userRepository.findAll();
        } else
            throw new UserNotFoundException("There are no users in database");
    }

    public List<User> getByFirstNameOrLastName(String firstname, String lastName) throws UserNotFoundException {
        if (userRepository.findByFirstNameOrLastName(firstname, lastName).size() > 0) {
            return userRepository.findByFirstNameOrLastName(firstname, lastName);
        } else
            throw new UserNotFoundException("User with that first or last name not found!");
    }

    public List<User> getByRole(UserRole role) throws UserNotFoundException {
        if (userRepository.findByRole(role).size() > 0) {
            return userRepository.findByRole(role);
        } else
            throw new UserNotFoundException("User with " + role + " role not found!");
    }

    @Transactional
    public User add(User user) throws UserExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException("User with '" + user.getEmail() + "' email already exists!");
        } else
            return userRepository.save(user);
    }

    @Transactional
    public User update(long id, User userDetails) throws UserNotFoundException, UserExistsException {
        User user = userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User with " + id + " ID not found!"));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException("User with '" + user.getEmail() + "' email already exists!");
        } else {
            return userRepository.save(user);
        }
    }

    @Transactional
    public void delete(long id) throws UserNotFoundException {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else
            throw new UserNotFoundException("User with " + id + " ID not found!");
    }
}
