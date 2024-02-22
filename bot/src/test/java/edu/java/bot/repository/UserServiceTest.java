package edu.java.bot.repository;

import edu.java.bot.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static edu.java.bot.models.SessionState.BASE_STATE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserService userService;
    @Mock
    User user;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void saveUser_shouldStoreUser_whenUserIsValid() {
        user = new User(1L, BASE_STATE);

        userService.saveUser(user);

        Optional<User> retrievedUser = userService.findUserById(1L);
        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
    }

    @Test
    void findUserByIdWhenUserExists() {

        user = new User(1L, BASE_STATE);
        userService.saveUser(user);
        Optional<User> retrievedUser = userService.findUserById(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
    }

    @Test
    void findUserById() {
        Optional<User> retrievedUser = userService.findUserById(2L);

        assertTrue(retrievedUser.isEmpty());
    }
}
