package sea.nat.ashesi.healthhubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "MedicalRecord")
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordId;
    private int pulseRate;
    private int bloodPressure;
    private int temperature;
    private int glucoseLevel;
    private LocalDate dateCreated;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id_fk")
    private Patient patient;

}
