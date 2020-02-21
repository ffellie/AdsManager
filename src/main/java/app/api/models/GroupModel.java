package app.api.models;

import app.data.ad.Ad;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonSerialize
public class GroupModel {
    private String name;
    List<Ad> ads;
}
