package app;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 *
 */
@EnableVaadin
@Configuration
@SpringBootApplication
@EnableWebMvc
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}