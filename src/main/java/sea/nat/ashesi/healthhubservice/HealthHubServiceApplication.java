package sea.nat.ashesi.healthhubservice;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.model.Sex;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class HealthHubServiceApplication {

	private PatientRepository patientRepository;

	public static void main(String[] args) {
		try {
			SpringApplication.run(HealthHubServiceApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Bean
	public CommandLineRunner init() {
		return args -> {
			var patient= Patient.builder()
					.firstNames("Sam Micheal")
					.surname("Mensah")
					.nationality("Ghanaian")
					.personalIdNumber("555-1234")
					.sex(Sex.Male)
					.signUpDate(LocalDate.now())
					.dateOfBirth(LocalDate.parse("1980-11-14"))
					.height(1.7)
					.build();

			var patient2= Patient.builder()
					.firstNames("Daniella")
					.surname("Donkor")
					.nationality("Ghanaian")
					.personalIdNumber("123-567")
					.sex(Sex.Female)
					.signUpDate(LocalDate.now())
					.dateOfBirth(LocalDate.parse("1999-04-21"))
					.height(1.5)
					.build();

			var patient3= Patient.builder()
					.firstNames("Jamie")
					.surname("Otoo")
					.nationality("Ghanaian")
					.personalIdNumber("242-567")
					.sex(Sex.Male)
					.signUpDate(LocalDate.now())
					.dateOfBirth(LocalDate.parse("2000-05-01"))
					.height(1.2)
					.build();

			var patient4= Patient.builder()
					.firstNames("Marcus")
					.surname("Richardson")
					.nationality("Ghanaian")
					.personalIdNumber("242-567")
					.sex(Sex.Male)
					.signUpDate(LocalDate.now())
					.dateOfBirth(LocalDate.parse("1990-11-14"))
					.height(1.0)
					.build();

			var patient5= Patient.builder()
					.firstNames("Daniel")
					.surname("Bedu")
					.nationality("Ghanaian")
					.personalIdNumber("242-567")
					.sex(Sex.Male)
					.signUpDate(LocalDate.now())
					.dateOfBirth(LocalDate.parse("2003-10-10"))
					.height(1.8)
					.build();

			patientRepository.save(patient);
			patientRepository.save(patient2);
			patientRepository.save(patient3);
			patientRepository.save(patient3);
			patientRepository.save(patient5);

		};
	}

}
