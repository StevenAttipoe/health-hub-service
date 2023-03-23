package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/record")
public class MedicalRecordController {
    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/get/recent")
    public ResponseEntity<MedicalRecord> getRecord(){
        return ResponseEntity.ok(medicalRecordService.getRecentMedicalRecord(patientService.getPatient().getPatientId()));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<MedicalRecordDto>> getRecords(){
        return ResponseEntity.ok(medicalRecordService.getMedicalRecords(patientService.getPatient().getPatientId()));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createRecord(@RequestBody MedicalRecordDto medicalRecordDto){
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(medicalRecordDto));
    }

    @PutMapping("/update")
    public ResponseEntity<MedicalRecord> addAnnotation(@RequestBody String annotation) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(annotation));
    }
}
