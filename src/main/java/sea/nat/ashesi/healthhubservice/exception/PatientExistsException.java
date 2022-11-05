package sea.nat.ashesi.healthhubservice.exception;

public class PatientExistsException extends RuntimeException{
    public PatientExistsException(String message) {
        super(message);
    }
}
