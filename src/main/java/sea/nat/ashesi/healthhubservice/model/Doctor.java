package sea.nat.ashesi.healthhubservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Data
@Table(name = "doctor")
@Accessors(chain = true)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctor_id;
//    private String name;
//    @Column(length = 100)
    private String email ;
    private String password;
}
