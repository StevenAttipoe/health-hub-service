package sea.nat.ashesi.healthhubservice.exception;

public class UserException extends RuntimeException{
    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
