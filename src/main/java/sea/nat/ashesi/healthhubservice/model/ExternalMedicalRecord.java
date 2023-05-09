package sea.nat.ashesi.healthhubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@Table(name = "ExternalMedicalRecord")
@AllArgsConstructor
@NoArgsConstructor
public class ExternalMedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordId;
    private String personalIdNumber;
    private String field;
    private String value;
    private boolean isChecked;
    private String notes;
    private LocalDate dateCreated;
    private LocalTime timeCreated;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id_fk")
    private Patient patient;
}
