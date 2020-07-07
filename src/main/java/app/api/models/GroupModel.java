package app.api.models;

import app.data.ad.Ad;
import app.data.promotion.Promotion;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@JsonSerialize
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupModel implements Serializable {
    private String name;
    List<PromotionModel> ads;
}
