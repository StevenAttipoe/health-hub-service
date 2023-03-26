package sea.nat.ashesi.healthhubservice.dto.response;

import lombok.*;

import java.util.Map;


@Builder
@Value
public class ReportDto {
    int noOfCheckIns;
    int noOfPatients;
    int noOfNewRegisters;
    int noOfUncheckedRecords;
    int noOfCheckedRecords;
    int noOfMalePatients;
    int noOfFemalePatients;
    Map<String, Integer> noOfRecordsPerMonth;
}
