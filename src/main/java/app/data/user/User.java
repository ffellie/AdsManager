package app.data.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String login;

    @Column
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

}