package app.data.promotion;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int duration = 30;

    private Time start = Time.valueOf(LocalTime.of(9,0));

    private Time end = Time.valueOf(LocalTime.of(18,0));

    //    private Date startDate;
//    private Date endDate;

    private Integer minutes = 0;

    private Long adID;

    private Long groupId;
}
