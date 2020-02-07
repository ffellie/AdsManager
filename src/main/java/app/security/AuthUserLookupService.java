package app.security;

import app.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserLookupService {
    @Autowired
    UserRepository userDAO;

    User findUser (String login){
        Optional<app.data.User> user = userDAO.findByLogin(login);
        if (user.isPresent()) {
            app.data.User user1 = user.get();
            return new User(user1.getLogin(),user1.getPassword());
       }
        return null;
        }
}
