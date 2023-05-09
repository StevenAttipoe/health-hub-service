package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sea.nat.ashesi.healthhubservice.dto.request.ExternalMedicalRecordDto;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;

@RequestMapping("api/v1/external")
@AllArgsConstructor
@RestController
public class ExternalController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createExternalRecord(@RequestBody ExternalMedicalRecordDto externalMedicalRecordDto){
        return ResponseEntity.ok(medicalRecordService.createExternalMedicalRecord(externalMedicalRecordDto));
    }
}
