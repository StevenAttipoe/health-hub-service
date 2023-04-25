package sea.nat.ashesi.healthhubservice.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sea.nat.ashesi.healthhubservice.exception.MedicalRecordException;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;
import sea.nat.ashesi.healthhubservice.utils.MedicalRecordConvertor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MedicalRecordServiceImplTest {
    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    private MedicalRecordConvertor medicalRecordConvertor;

    @Mock
    private PatientService patientService;

    MedicalRecord mockMedicalRecord;
    Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        medicalRecordConvertor = new MedicalRecordConvertor(patientService);

        patient = new Patient();
        patient.setPatientId(1L);

        mockMedicalRecord = MedicalRecord.builder()
                .recordId(1L)
                .patient(patient)
                .notes("mock nots")
                .dateCreated(LocalDate.from(LocalDateTime.now()))
                .build();
    }


    @Test
    void getRecentMedicalRecord_returnsMedicalRecord_whenExists() {

        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(mockMedicalRecord);
        when(medicalRecordRepository.findFirstByPatientPatientIdOrderByDateCreatedDesc(patient.getPatientId()))
                .thenReturn(optionalMedicalRecord);

        MedicalRecord result = medicalRecordService.getRecentMedicalRecord(patient.getPatientId());

        assertEquals(mockMedicalRecord, result);
    }

    @Test
    void getRecentMedicalRecord_throwsException_whenNotExists() {
        long patientId = 1L;
        Optional<MedicalRecord> optionalMedicalRecord = Optional.empty();
        when(medicalRecordRepository.findFirstByPatientPatientIdOrderByDateCreatedDesc(patientId))
                .thenReturn(optionalMedicalRecord);

        assertThrows(MedicalRecordException.class, () -> medicalRecordService.getRecentMedicalRecord(patientId));
    }

    @Test
    void updateMedicalRecord_returnsUpdatedRecord_whenExists() {
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(mockMedicalRecord);
        when(medicalRecordRepository.findById(mockMedicalRecord.getRecordId()))
                .thenReturn(optionalMedicalRecord);
        when(medicalRecordRepository.save(mockMedicalRecord)).thenReturn(mockMedicalRecord);

        MedicalRecord result = medicalRecordService
                .updateMedicalRecord("updated notes", mockMedicalRecord.getRecordId());

        assertEquals("updated notes", result.getNotes());
        assertFalse(result.isChecked());
    }

    @Test
    void updateMedicalRecord_throwsException_whenNotExists() {
        long medicalRecordId = 1L;
        Optional<MedicalRecord> optionalMedicalRecord = Optional.empty();
        when(medicalRecordRepository.findById(medicalRecordId))
                .thenReturn(optionalMedicalRecord);

        assertThrows(MedicalRecordException.class,
                () -> medicalRecordService.updateMedicalRecord("updated notes", medicalRecordId));
    }

    @Test
    void approveMedicalRecord_returnsTrue_whenExists() {
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(mockMedicalRecord);
        when(medicalRecordRepository.findById(mockMedicalRecord.getRecordId()))
                .thenReturn(optionalMedicalRecord);
        when(medicalRecordRepository.save(mockMedicalRecord)).thenReturn(mockMedicalRecord);

        boolean result = medicalRecordService.approveMedicalRecord(mockMedicalRecord.getRecordId());

        assertTrue(result);
        assertTrue(mockMedicalRecord.isChecked());
    }

    @Test
    void approveMedicalRecord_throwsException_whenNotExists() {
        long medicalRecordId = 1L;
        when(medicalRecordRepository.findById(medicalRecordId)).thenReturn(Optional.empty());
        MedicalRecordServiceImpl medicalRecordService =
                new MedicalRecordServiceImpl(medicalRecordRepository, medicalRecordConvertor);

        assertThrows(MedicalRecordException.class, () -> medicalRecordService.approveMedicalRecord(medicalRecordId));
    }
}