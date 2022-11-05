package sea.nat.ashesi.healthhubservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Table(name = "patients")
@Accessors(chain = true)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patient_id;
    private String patient_name;
    @Column(length = 100)
    private String patient_email;
    private String patient_password;
}
