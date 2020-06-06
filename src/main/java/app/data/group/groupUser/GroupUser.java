package app.data.group.groupUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "groups_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupUser {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    @NotNull
    private Long groupId;
    @Column
    @NotNull
    private Long user;
}
