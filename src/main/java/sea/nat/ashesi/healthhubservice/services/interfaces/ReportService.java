package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.response.ReportDto;

public interface ReportService {
    ReportDto getLatestReport();
}
