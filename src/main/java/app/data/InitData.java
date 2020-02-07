package app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements ApplicationRunner {
    private UserRepository userDAO;
    @Override
    public void run (ApplicationArguments args){
        User admin = new User("admin","admin");
        userDAO.save(admin);
    }

    @Autowired
    public InitData (UserRepository userDAO){
        this.userDAO=userDAO;
    }
}
