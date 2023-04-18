package sea.nat.ashesi.healthhubservice.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorLogInDto;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Gender;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;
import sea.nat.ashesi.healthhubservice.utils.DoctorConvertor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class DoctorServiceImplTest {
    @Mock
    DoctorRepository doctorRepository;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @Autowired
    DoctorService underTest;

    @InjectMocks
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    DoctorConvertor doctorConvertor;

    DoctorSignUpDto doctorSignUpDto;

    @Autowired
    Doctor doctor;

    String jwtToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doctorConvertor = new DoctorConvertor(passwordEncoder);

        underTest = new DoctorServiceImpl(doctorRepository, doctorConvertor, authenticationManager, jwtService);

        doctor = Doctor.builder()
                .fullName("Mock Test")
                .password("test-password")
                .email("mock@test.com")
                .experienceInYears(2)
                .gender(Gender.M)
                .phoneNumber(1234567890)
                .speciality("Cardiology")
                .patients(List.of(Mockito.mock(Patient.class)))
                .build();

        doctorSignUpDto = DoctorSignUpDto.builder()
                .fullName("John Doe")
                .password("test-password")
                .email("john.doe@mail.com")
                .experienceInYears(2)
                .gender(Gender.M)
                .phoneNumber(1234567890)
                .speciality("Cardiology")
                .build();

        jwtToken = "mockJwtToken";
    }

    @Test
    void shouldSignUpDoctor() {
        //WHEN
        lenient().when(doctorRepository.findByEmail(doctorSignUpDto.getEmail()))
                .thenReturn(Optional.empty());

        var doctor = doctorConvertor.convert(doctorSignUpDto);

        lenient().when(doctorRepository.save(doctor)).thenReturn(Mockito.mock(Doctor.class));
        lenient().when(jwtService.generateToken(any())).thenReturn("mockJwtToken");


        //THEN
        String result = underTest.signUpDoctor(doctorSignUpDto);
        Assertions.assertEquals(jwtToken, result);
    }

    @Test
    void shouldNotSignUpDoctorIfDoctorExists() {
        //WHEN
        lenient().when(doctorRepository.findByEmail(doctorSignUpDto.getEmail()))
                .thenReturn(Optional.ofNullable(doctorConvertor.convert(doctorSignUpDto)));

        //THEN
        assertThrows(UserException.class, () -> underTest.signUpDoctor(doctorSignUpDto));
    }

    @Test
    void testAuthenticateDoctor() {
        // GIVEN
        DoctorLogInDto doctorLogInDto = DoctorLogInDto.builder()
                .password("test-password")
                .email("mock@test.com")
                .build();

        // WHEN
        lenient().when(doctorRepository.findByEmail(doctorLogInDto.getEmail())).thenReturn(Optional.of(doctor));
        lenient().when(jwtService.generateToken(any())).thenReturn(jwtToken);

        // WHEN
        String result = underTest.authenticateDoctor(doctorLogInDto);

        // THEN
        Assertions.assertEquals("mockJwtToken", result);
    }

    @Test
    void testGetDoctorWithExistingDoctor() {
        // GIVEN
        lenient().when(doctorRepository.findByEmail("test@test.com")).thenReturn(Optional.of(doctor));

        // WHEN
        Doctor result = underTest.getDoctor("test@test.com");

        // THEN
        Assertions.assertEquals(doctor, result);
    }

    @Test
    void testGetDoctorWithNonExistingDoctor() {
        // GIVEN
        lenient().when(doctorRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(UserException.class, () -> underTest.getDoctor("test@test.com"));
    }

    @Test
    void testGetNextDoctorWithNoDoctors() {
        // GIVEN
        lenient().when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // WHEN + THEN
        Assertions.assertNull(underTest.getNextDoctor());
    }

    @Test
    void testGetNextDoctorWithOneDoctor() {
        // GIVEN
        lenient().when(doctorRepository.findAll()).thenReturn(Collections.singletonList(doctor));

        // WHEN
        Doctor result = underTest.getNextDoctor();

        // THEN
        Assertions.assertEquals(doctor, result);
    }

    @Test
    void testGetNextDoctorWithMultipleDoctors() {
        // GIVEN
        doctor.setPatients(List.of(Mockito.mock(Patient.class), Mockito.mock(Patient.class)));
        Doctor doctor1 = doctor;

        doctor.setPatients(List.of(Mockito.mock(Patient.class), Mockito.mock(Patient.class), Mockito.mock(Patient.class)));
        Doctor doctor2 = doctor;

        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        lenient().when(doctorRepository.findAll()).thenReturn(doctors);


        // WHEN
        Doctor result = underTest.getNextDoctor();

        // THEN
        Assertions.assertEquals(doctor2, result);
    }

}