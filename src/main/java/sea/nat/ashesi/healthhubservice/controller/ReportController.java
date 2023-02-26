package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sea.nat.ashesi.healthhubservice.model.Report;
import sea.nat.ashesi.healthhubservice.services.interfaces.ReportService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/report")
public class ReportController {
    public final ReportService reportService;

    @GetMapping("/getLatest")
    public ResponseEntity<Report> getDoctorReport(){
        return ResponseEntity.ok(reportService.getLatestReport());
    }
}
