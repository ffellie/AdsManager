package app.data;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private int duration;
    private MediaType mediaType;
    private String filename;
}
