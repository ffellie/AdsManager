package app.data.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "time_intervals")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int start;
    private int end;
    private int minutes;
    private long adID;
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotNull
    @JsonIgnore
    private Group group;
}
