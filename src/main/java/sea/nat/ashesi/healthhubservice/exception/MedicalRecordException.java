package sea.nat.ashesi.healthhubservice.exception;

public class MedicalRecordException extends RuntimeException{
    public MedicalRecordException(String errorMessage) {
        super(errorMessage);
    }
}
