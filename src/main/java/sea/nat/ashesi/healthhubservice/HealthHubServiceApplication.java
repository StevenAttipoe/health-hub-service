package sea.nat.ashesi.healthhubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthHubServiceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(HealthHubServiceApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
