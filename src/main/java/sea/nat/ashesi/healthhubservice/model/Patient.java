package sea.nat.ashesi.healthhubservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "Patient")
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String surname;
    private String firstNames;
    private String nationality;
    private Sex sex;
    private LocalDate dateOfBirth;
    private double height;
    private String personalIdNumber;
    private String documentNumber;
    private String placeOfIssuance;
    private LocalDate dateOfIssuance;
    private LocalDate dateOfExpiry;
    private LocalDate signUpDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id_fk")
    private Doctor doctor;

    @OneToMany(mappedBy="patient")
    private List<MedicalRecord> medicalRecords;
}


