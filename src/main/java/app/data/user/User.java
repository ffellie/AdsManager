package app.data.user;

import app.data.ad.Ad;
import app.data.group.Group;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue
    private long id;
    @Column (nullable = false, unique = true)
    private String name;
    @Column
    private String password;
    @Column
    private UserRole role;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Ad> ads = new ArrayList<>();

}
