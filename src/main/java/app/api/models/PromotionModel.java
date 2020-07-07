package app.api.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@JsonSerialize
@NoArgsConstructor
@Builder
public class PromotionModel implements Serializable {
    private int duration = 30;

    private Time start;

    private Time end;

    private Integer minutes;

    private String url;

    private String mediaType;
}
