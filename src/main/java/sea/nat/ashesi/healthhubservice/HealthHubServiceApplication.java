package sea.nat.ashesi.healthhubservice;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@AllArgsConstructor
public class HealthHubServiceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(HealthHubServiceApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
