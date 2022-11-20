package sea.nat.ashesi.healthhubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.User;
import sea.nat.ashesi.healthhubservice.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean signUpUser(User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent()) {
            throw new UserException("A user with this email already exists");
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean loginUser(User user) {
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        if(foundUser.isPresent()) {
            if(foundUser.get().getPassword().equals(user.getPassword())){
                return true;
            }
        }
        throw new UserException("User does not exist");
    }
}
