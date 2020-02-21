package app.data;

import app.data.user.User;
import app.data.user.UserRepository;
import app.data.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements ApplicationRunner {
    private UserRepository userDAO;
    @Override
    public void run (ApplicationArguments args){
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRole(UserRole.ADMIN);
        userDAO.save(admin);
    }

    @Autowired
    public InitData (UserRepository userDAO){
        this.userDAO=userDAO;
    }
}
