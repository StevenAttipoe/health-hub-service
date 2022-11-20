package sea.nat.ashesi.healthhubservice.services;

import sea.nat.ashesi.healthhubservice.model.User;

public interface UserService {
    boolean signUpUser(User user);

    boolean loginUser(User user);

}
