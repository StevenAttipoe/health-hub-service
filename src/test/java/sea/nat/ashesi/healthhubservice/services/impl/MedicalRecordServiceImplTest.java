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


//    @Test
//    void createMedicalRecord() {
//        var request = MedicalRecordDto.builder()
//                .recordId(1L)
//                .notes("mock nots")
//                .dateCreated(LocalDate.from(LocalDateTime.now()))
//                .build();
//
////        when(patientService.getPatient()).thenReturn(patient);
//        when(medicalRecordConvertor.convert(request)).thenReturn(mockMedicalRecord);
//        when(medicalRecordRepository.save(mockMedicalRecord)).thenReturn(mockMedicalRecord);
//
//        boolean result = medicalRecordService.createMedicalRecord(request);
//
//        assertTrue(result);
//        verify(medicalRecordRepository).save(mockMedicalRecord);
//    }

//    @Test
//    void getMedicalRecords() {
//        long patientId = 1L;
//        int pageNo = 0;
//        Pageable pageable = PageRequest.of(pageNo, 4, (Sort.by("isChecked")
//                .and(Sort.by("dateCreated").descending()))
//                .and(Sort.by("timeCreated").descending()));
//
//        Page<MedicalRecord> medicalRecordsEntities = new PageImpl<>(Collections.emptyList());
//        when(medicalRecordRepository.findByPatientPatientId(patientId, pageable)).thenReturn(medicalRecordsEntities);
//
//        List<MedicalRecordDto> result = medicalRecordService.getMedicalRecords(patientId, pageNo);
//
//        assertNotNull(result);
//        assertEquals(Collections.emptyList(), result);
//        verify(medicalRecordRepository).findByPatientPatientId(patientId, pageable);
//    }

//    @Test
//    void getMedicalRecordsByMonth() {
//        long doctorId = 1L;
//        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
//        LocalDate endDate = LocalDate.of(2023, Month.JANUARY, 31);
//        int count = 1;
//        Map<String, Integer> expectedRecordsByMonth = new LinkedHashMap<>();
//        expectedRecordsByMonth.put(Month.JANUARY.toString(), count);
//        expectedRecordsByMonth.put(Month.JANUARY.toString(), count);
//        expectedRecordsByMonth.put(Month.JANUARY.toString(), count);
//        expectedRecordsByMonth.put(Month.JANUARY.toString(), count);
//
//        when(medicalRecordRepository.countByDateCreatedBetweenAndPatientDoctorDoctorId(startDate, endDate, doctorId))
//                .thenReturn(count);
//
//        Map<String, Integer> result = medicalRecordService.getMedicalRecordsByMonth(doctorId);
//
//        assertNotNull(result);
//        assertEquals(expectedRecordsByMonth, result);
//        verify(medicalRecordRepository, times(Month.values().length)).countByDateCreatedBetweenAndPatientDoctorDoctorId(any(LocalDate.class), any(LocalDate.class), eq(doctorId));
//    }

//    @Test
//    void getTotalPage() {
//        Pageable pageable = PageRequest.of(0, 4, Sort.by("timeCreated"));
//        Page<MedicalRecord> medicalRecordsEntities = new PageImpl<>(Collections.emptyList());
//        when(medicalRecordRepository.findByPatientPatientId(patient.getPatientId(), pageable))
//                .thenReturn(medicalRecordsEntities);
//
//        int result = medicalRecordService.getTotalPage(0, patient.getPatientId());
//
//        assertEquals(0, result);
//        verify(medicalRecordRepository).findByPatientPatientId(patient.getPatientId(), pageable);
//    }

}