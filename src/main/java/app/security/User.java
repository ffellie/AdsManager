package app.security;


import app.data.user.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Getter
@Setter
public class User implements UserDetails {

    private static final String USE_APP_ROLE = "USE-APP-ROLE";

    private static final long serialVersionUID = 1L;

    private String password;
    private String name;
    private UserRole role;

    public User(String login, String password, UserRole role) {
        super();
        this.name = login;
        this.password = password;
        this.role = role;
    }

    @SuppressWarnings("serial")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        result.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return USE_APP_ROLE;
            }

        });

        return result;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }



}