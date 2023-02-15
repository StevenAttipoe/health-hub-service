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
					.dateOfBirth(LocalDate.now())
					.height(1.7)
					.build();

			var patient2= Patient.builder()
					.firstNames("Daniella")
					.surname("Donkor")
					.nationality("Ghanaian")
					.personalIdNumber("123-567")
					.sex(Sex.Female)
					.dateOfBirth(LocalDate.now())
					.height(1.5)
					.build();

			patientRepository.save(patient);
			patientRepository.save(patient2);
		};
	}

}
