package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.response.ReportDto;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository recordRepository;
    private final MedicalRecordService medicalRecordService;
    private final DoctorService doctorService;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    @Override
    public ReportDto getLatestReport() {
        String token = request.getHeader("Authorization").substring(7);
        var doctor = doctorService.getDoctor(jwtService.extractUsername(token));

        int totalNoOfPatients = patientRepository.countByDoctorDoctorId(doctor.getDoctorId());
        int dailyNoOfSignUps = patientRepository.countBySignUpDateAndDoctorDoctorId(LocalDate.now(), doctor.getDoctorId());
        int dailyNoOfRecords = recordRepository.countByDateCreatedAndPatientDoctorDoctorId(LocalDate.now(),doctor.getDoctorId());
        int noOfMalePatients = patientRepository.countMalePatientsByDoctorId(doctor.getDoctorId());
        int noOfFemalePatients = patientRepository.countFemalePatientsByDoctorId(doctor.getDoctorId());
        int noOfCheckedRecords = Math.toIntExact(recordRepository.countCheckedMedicalRecordsForDoctor(doctor.getDoctorId()));
        int noOfUncheckedRecords = Math.toIntExact(recordRepository.countUncheckedMedicalRecordsForDoctor(doctor.getDoctorId()));
        Map<String, Integer> noOfRecordsPerMonth = medicalRecordService.getMedicalRecordsByMonth(doctor.getDoctorId());
        return  ReportDto.builder()
                .noOfCheckIns(dailyNoOfRecords)
                .noOfNewRegisters(dailyNoOfSignUps)
                .noOfPatients(totalNoOfPatients)
                .noOfCheckedRecords(noOfCheckedRecords)
                .noOfUncheckedRecords(noOfUncheckedRecords)
                .noOfMalePatients(noOfMalePatients)
                .noOfFemalePatients(noOfFemalePatients)
                .noOfRecordsPerMonth(noOfRecordsPerMonth)
                .build();
    }
}
