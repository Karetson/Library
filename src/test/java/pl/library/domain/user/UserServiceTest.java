package pl.library.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
    UserService systemUnderTest;
    Set<Role> roles = new HashSet<>();
    Set<Book> favorites = new HashSet<>();
    static final Long ID = 1L;

    User user = new User(ID,
            "firstName",
            "lastName",
            "email@email.com",
            "password",
            roles,
            favorites,
            null,
            null,
            null);

    Book book = new Book(ID,
            "title",
            "author",
            "publisher",
            null,
            10,
            8,
            true,
            "desc");

    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.systemUnderTest = new UserService(userRepository, bookRepository, roleRepository, bCryptPasswordEncoder,
                authenticationManager, jwtTokenUtil, jwtUserDetailsService);
    }

    @Test
    void shouldReturnUserBasedOnId() throws UserNotFoundException {
        // given
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        // when
        User gainedUser = systemUnderTest.getUserById(ID);

        // then
        assertThat(gainedUser).isEqualTo(user);
    }

    @Test
    void shouldNotReturnUserBasedOnIdWhenUserIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getUserById(ID)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnUserBasedOnEmail() throws UserNotFoundException {
        // given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        User gainedUser = systemUnderTest.getUserByEmail(user.getEmail());

        // then
        assertThat(gainedUser).isEqualTo(user);
    }

    @Test
    void shouldNotReturnUserBasedOnEmailWhenUserIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.getUserByEmail(user.getEmail())).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldReturnUserBasedOnEmailAndPassword() throws UserNotFoundException {
        // given
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        User loggedUser = systemUnderTest.loginUser(this.user.getEmail(), this.user.getPassword());

        // then
        assertThat(loggedUser).isEqualTo(user);
    }

    @Test
    void shouldNotReturnUserBasedOnEmailAndPasswordWhenUserIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.loginUser(user.getEmail(), user.getPassword()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldCreateUser() throws UserExistsException {
        // given
        when(userRepository.save(user)).thenReturn(user);

        // when
        User registeredUser = systemUnderTest.registerUser(this.user);

        // then
        assertThat(registeredUser).isEqualTo(user);
    }

    @Test
    void shouldNotCreateUserWhenUserEmailAlreadyExists() {
        // given
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.registerUser(user)).isInstanceOf(UserExistsException.class);
    }

    @Test
    void shouldUpdateUserProfile() throws UserNotFoundException, UserExistsException {
        // given
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // when
        User updatedUser = systemUnderTest.updateUserProfile(ID, user);

        // then
        assertThat(updatedUser).isEqualTo(user);
    }

    @Test
    void shouldNotUpdateUserProfileWhenUserIsNotFound() {
        // given

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.updateUserProfile(ID, user)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldNotUpdateUserProfileWhenUserEmailChangedToAlreadyExistsEmail() {
        // given
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.updateUserProfile(ID, user)).isInstanceOf(UserExistsException.class);
    }

    @Test
    void shouldAddFavoriteBookToUser() throws UserNotFoundException {
        // given
        favorites.add(book);
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(userRepository.save(user)).thenReturn(new User());

        // when
        User updatedUser = systemUnderTest.addFavoriteBookToUser(ID, ID);

        // then
        assertThat(updatedUser).isInstanceOf(User.class);
    }

    @Test
    void shouldNotAddFavoriteBookToUserWhenUserIsNotFound() {
        // given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(new Book()));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addFavoriteBookToUser(ID, ID)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldNotAddFavoriteBookToUserWhenBookIsNotFound() {
        // given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.addFavoriteBookToUser(ID, ID)).isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void shouldSubtractFavoriteBookFromUser() throws UserNotFoundException {
        // given
        favorites.add(book);
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        when(userRepository.save(user)).thenReturn(new User());

        // when
        systemUnderTest.subtractFavoriteBookFromUser(ID, ID);

        // then
        verify(userRepository).save(user);
    }

    @Test
    void shouldNotSubtractFavoriteBookToUserWhenUserIsNotFound() {
        // given
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(new Book()));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.subtractFavoriteBookFromUser(ID, ID)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldNotSubtractFavoriteBookToUserWhenBookIsNotFound() {
        // given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));

        // when

        // then
        assertThatThrownBy(() -> systemUnderTest.subtractFavoriteBookFromUser(ID, ID)).isInstanceOf(BookNotFoundException.class);
    }
}