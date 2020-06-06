package app.data.group.groupAd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_ad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupAd {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long groupId;
    @Column
    private Long ad;
}
