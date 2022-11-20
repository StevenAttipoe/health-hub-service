package sea.nat.ashesi.healthhubservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.User;
import sea.nat.ashesi.healthhubservice.repositories.UserRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Autowired
    UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldSignUpUser(){
        //GIVEN
        User user = new User()
                .setPatient_id(67082023)
                .setName("Steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        lenient().when(userRepository.save(user)).thenReturn(new User());
        boolean result = underTest.signUpUser(user);

        //THEN
        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldNotSignUpUserIfUserExists(){
        //GIVEN
        User user = new User()
                .setPatient_id(67082023)
                .setName("steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new User()));
        lenient().when(userRepository.save(user)).thenReturn(new User());

        //THEN
        assertThrows(UserException.class, () -> underTest.signUpUser(user));
    }

    @Test
    void shouldLogInUser(){
        //GIVEN
        User user = new User()
                .setPatient_id(67082023)
                .setName("Steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        boolean result = underTest.loginUser(user);

        //THEN
        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldNotLogInUser(){
        //GIVEN
        User user = new User()
                .setPatient_id(67082023)
                .setName("Steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        //THEN
        assertThrows(UserException.class, () -> underTest.loginUser(user));
    }

}