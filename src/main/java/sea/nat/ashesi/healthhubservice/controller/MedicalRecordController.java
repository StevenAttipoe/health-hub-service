package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getRecords(){
        var patientID = patientService.getPatient().getPatientId();
        Map<String, Object> response = new HashMap<>();
        List<MedicalRecordDto> medicalRecordsDtoList = medicalRecordService.getAllMedicalRecords(patientID);

        response.put("records", medicalRecordsDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/id/all/")
    public ResponseEntity<Map<String, Object>> getRecords(
            @RequestParam String patientId,
            @RequestParam(defaultValue = "0") int pageNo){

        var patientID= patientService.getPatient(patientId).getPatientId();
        Map<String, Object> response = new HashMap<>();
        List<MedicalRecordDto> medicalRecordsDtoList = medicalRecordService.getMedicalRecords(patientID, pageNo);
        response.put("records", medicalRecordsDtoList);
        response.put("totalPages", medicalRecordService.getTotalPage(pageNo, patientID));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createRecord(@RequestBody MedicalRecordDto medicalRecordDto){
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(medicalRecordDto));
    }

    @PutMapping("/update")
    public ResponseEntity<MedicalRecord> addAnnotation(@RequestBody String notes, @RequestParam String recordId) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(notes, Long.parseLong(recordId)));
    }

    @PutMapping("/approve")
    public ResponseEntity approveRecord(@RequestParam String recordId) {
        return ResponseEntity.ok(medicalRecordService.approveMedicalRecord(Long.parseLong(recordId)));
    }
}
