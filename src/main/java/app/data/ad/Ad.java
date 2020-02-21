package app.data.ad;

import app.data.group.Group;
import app.data.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "ads")
@Data
@JsonIgnoreProperties
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull(message = "name is null")
    @Column(name = "name", unique = true)
    private String name;
    private String description;
    private int duration;
    private MediaType mediaType;
    private String filename;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
}
