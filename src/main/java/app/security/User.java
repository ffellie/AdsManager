package app.security;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class User implements UserDetails {

    private static final String USE_APP_ROLE = "USE-APP-ROLE";

    private static final long serialVersionUID = 1L;

    private String password;
    private String login;

    public User(String login, String password) {
        super();
        this.login = login;
        this.password = password;
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
        return login;
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





}