package app.data.ad;

import app.data.group.Group;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ads")
@Getter
@Setter
public class Ad {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private int duration;
    private MediaType mediaType;
    private String filename;
    @ManyToMany
    private Set<Group> groups;
}
