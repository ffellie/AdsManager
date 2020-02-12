package app.security;

import app.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserLookupService {
    @Autowired
    UserRepository userDAO;

    User findUser (String login){
        Optional<app.data.user.User> user = userDAO.findByLogin(login);
        if (user.isPresent()) {
            app.data.user.User user1 = user.get();
            return new User(user1.getLogin(),user1.getPassword());
       }
        return null;
        }
}
