package sea.nat.ashesi.healthhubservice.model;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patient_id;
    private String name;
    @Column(length = 100)
    private String email ;
    private String password;
}
