package sea.nat.ashesi.healthhubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.model.Report;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository recordRepository;

    @Override
    public Report getLatestReport() {
        int totalNoOfPatients = (int) patientRepository.count();
        int dailyNoOfSignUps = (int) patientRepository.countBySignUpDate(LocalDate.now());
        int dailyNoOfRecords = (int) recordRepository.countByDateCreated(LocalDate.now());
        var report = Report.builder()
                .noOfCheckIns(dailyNoOfRecords)
                .noOfNewRegisters(dailyNoOfSignUps)
                .noOfPatients(totalNoOfPatients)
                .build();
        return report;
    }
}
