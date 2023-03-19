package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.dto.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

@RestController
@AllArgsConstructor
@RequestMapping("ap1/v1/medical-record")
public class MedicalRecordController {
    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/get/recent")
    public ResponseEntity<MedicalRecord> getRecord(){
        return ResponseEntity.ok(medicalRecordService.getMedicalRecord(patientService.getPatient().getPatientId()));
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createRecord(@RequestBody MedicalRecordDto medicalRecordDto){
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(medicalRecordDto));
    }
}
