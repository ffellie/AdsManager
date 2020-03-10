package app.data.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "time_intervals")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int duration;
    private Time start;
    private Time end;
//    private Date startDate;
//    private Date endDate;
    private int minutes;
    private long adID;
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotNull
    @JsonIgnore
    private Group group;
}
