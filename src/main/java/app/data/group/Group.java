package app.data.group;

import app.data.ad.Ad;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private long id;

    private String url;

    @ManyToMany(mappedBy="groups")
    private List<Ad> ads;
}
