package sea.nat.ashesi.healthhubservice.services;

import org.junit.jupiter.api.Assertions;
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
import sea.nat.ashesi.healthhubservice.exception.PatientExistsException;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(SpringRunner.class)
class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;

    @Autowired
    PatientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PatientServiceImpl(patientRepository);
    }

    @Test
    void signUpUser(){
        //GIVEN
        Patient patient = new Patient()
                .setPatient_id(67082023)
                .setPatient_name("Steven")
                .setPatient_email("steven@gmail.com")
                .setPatient_password("abc");

        //WHEN
        lenient().when(patientRepository.findByEmail(patient.getPatient_email())).thenReturn(Optional.empty());
        lenient().when(patientRepository.save(patient)).thenReturn(new Patient());
        boolean result = underTest.signUpPatient(patient);

        //THEN
        assertThat(result).isEqualTo(true);
    }

    @Test
    void shouldNotSignUpUserIfUserExists(){
        //GIVEN
        Patient patient = new Patient()
                .setPatient_id(67082023)
                .setPatient_name("steven")
                .setPatient_email("steven@gmail.com")
                .setPatient_password("abc");

        //WHEN
        lenient().when(patientRepository.findByEmail(patient.getPatient_email())).thenReturn(Optional.of(new Patient()));
        lenient().when(patientRepository.save(patient)).thenReturn(new Patient());

        //THEN
        assertThrows(PatientExistsException.class, () -> underTest.signUpPatient(patient));
    }

}