package app.data.group;

import app.data.ad.Ad;
import app.data.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String url;

    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Promotion> promotions = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_ids", joinColumns=@JoinColumn(name="group_id"))
    @Column(name="user_ids")
    private Set<Long> userIDs = new TreeSet<>();

}
