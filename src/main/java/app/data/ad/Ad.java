package app.data.ad;

import app.data.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ads")
//@Data
@Setter
@Getter
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
    private MediaType mediaType;
    private String filename;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnore
    private User user;

    @Override
    public boolean equals(Object obj) {
        return true;
//        if (!obj.getClass().equals(this.getClass()))
////            return false;
//        Ad item = (Ad) obj;
//        return (item.getMediaType()==this.getMediaType()
//                && item.getFilename().equals(this.getFilename())
//                && item.getId()==this.getId()
//                && item.getName().equals(this.name)
//                && item.getDuration()==this.duration
//                && item.getDescription().equals(this.description));
    }
}
