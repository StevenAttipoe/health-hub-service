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
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class DoctorServiceTest {
    @Mock
    DoctorRepository doctorRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    DoctorService underTest;

    @BeforeEach
    void setUp() {
        underTest = new DoctorServiceImpl(doctorRepository, jwtTokenProvider);
    }

    @Test
    void shouldSignUpUser(){
        //GIVEN
        Doctor doctor = new Doctor()
                .setDoctor_id(67082023)
//                .setName("Steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.empty());
        lenient().when(doctorRepository.save(doctor)).thenReturn(new Doctor());
        boolean result = underTest.signUpDoctor(doctor);

        //THEN
        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldNotSignUpUserIfUserExists(){
        //GIVEN
        Doctor doctor = new Doctor()
                .setDoctor_id(67082023)
//                .setName("steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(new Doctor()));
        lenient().when(doctorRepository.save(doctor)).thenReturn(new Doctor());

        //THEN
        assertThrows(UserException.class, () -> underTest.signUpDoctor(doctor));
    }

//    @Test
//    void shouldLogInUser(){
//        //GIVEN
//        Doctor doctor = new Doctor()
//                .setPatient_id(67082023)
//                .setName("Steven")
//                .setEmail("steven@gmail.com")
//                .setPassword("abc");
//
//        //WHEN
//        lenient().when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));
//        String result = underTest.signInDoctor(doctor);
//
//        //THEN
//        assertThat(result).isEqualTo(true);
//    }

    @Test
    void shouldNotLogInUser(){
        //GIVEN
        Doctor doctor = new Doctor()
                .setDoctor_id(67082023)
//                .setName("Steven")
                .setEmail("steven@gmail.com")
                .setPassword("abc");

        //WHEN
        lenient().when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.empty());

        //THEN
        assertThrows(UserException.class, () -> underTest.signInDoctor(doctor));
    }

}