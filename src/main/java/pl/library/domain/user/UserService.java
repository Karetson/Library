package pl.library.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.role.Role;
import pl.library.adapters.mysql.model.user.User;
import pl.library.domain.book.exception.BookNotFoundException;
import pl.library.domain.book.repository.BookRepository;
import pl.library.domain.role.repository.RoleRepository;
import pl.library.domain.user.exception.UserExistsException;
import pl.library.domain.user.exception.UserNotFoundException;
import pl.library.domain.user.repository.UserRepository;
import pl.library.infrastructure.JwtTokenUtil;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&+=]).*$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
            Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with '" + id + "' ID not found!"));
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: '" + email + "' not found!"));
    }

    public User loginUser(String email, String password) throws UserNotFoundException {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
        String token = jwtTokenUtil.generateToken(userDetails);
        User loggedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        loggedUser.setJwtToken(token);

        return loggedUser;
    }

    @Transactional
    public User registerUser(User user) throws UserExistsException {
        if (!validateEmail(user.getEmail())) {
            throw new ValidationException("Incorrect email!");
        } else if (!validatePassword(user.getPassword())) {
            throw new ValidationException("Incorrect password");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException("User with '" + user.getEmail() + "' email already exists!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findByName("USER");
            user.setRoles(new HashSet<>(Collections.singletonList(role)));
            return userRepository.save(user);
        }
    }

    @Transactional
    public User updateUserProfile(Long id, User userDetails) throws UserNotFoundException, UserExistsException {
        User user = userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("User with " + id + " ID not found!"));

        if (!validateEmail(userDetails.getEmail())) {
            throw new ValidationException("Incorrect email!");
        } else if (!validatePassword(userDetails.getPassword())) {
            throw new ValidationException("Incorrect password");
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        user.setEmail(userDetails.getEmail());

        return userRepository.save(user);
    }

    @Transactional
    public User addFavoriteBookToUser(Long user_id, Long book_id) throws UserNotFoundException {
        User user = userRepository.findById(user_id).orElseThrow(()
                -> new UserNotFoundException("User with " + user_id + " ID not found!"));
        Book book = bookRepository.findById(book_id).orElseThrow(()
                -> new BookNotFoundException("Book with " + book_id + " ID not found!"));

        Set<Book> favorites = user.getFavoriteBooks();
        favorites.add(book);
        user.setFavoriteBooks(favorites);

        return userRepository.save(user);
    }

    @Transactional
    public void subtractFavoriteBookFromUser(Long user_id, Long book_id) throws UserNotFoundException {
        User user = userRepository.findById(user_id).orElseThrow(()
                -> new UserNotFoundException("User with " + user_id + " ID not found!"));
        Book book = bookRepository.findById(book_id).orElseThrow(()
                -> new BookNotFoundException("Book with " + book_id + " ID not found!"));

        Set<Book> favorites = user.getFavoriteBooks();
        favorites.remove(book);
        user.setFavoriteBooks(favorites);

        userRepository.save(user);
    }
}
