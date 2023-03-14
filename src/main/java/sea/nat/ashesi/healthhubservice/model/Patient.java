package sea.nat.ashesi.healthhubservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "Patient")
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String fullName;
    private String nationality;
    private Sex sex;
    private LocalDate dateOfBirth;
    private double height;
    private String placeOfIssuance;
    private LocalDate signUpDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id_fk")
    private Doctor doctor;

    @OneToMany(mappedBy="patient", cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return fullName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


