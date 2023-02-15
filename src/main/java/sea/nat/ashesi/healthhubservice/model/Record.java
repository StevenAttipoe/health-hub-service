package sea.nat.ashesi.healthhubservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "Medical_Record")
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long record_id;
    private int pulseRate;
    private int bloodPressure;
    private int temperature;
    private int glucoseLevel;
    private LocalDate date;
}
