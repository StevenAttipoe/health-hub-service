package sea.nat.ashesi.healthhubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private boolean isChecked;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private String annotation;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id_fk")
    private Patient patient;

}
