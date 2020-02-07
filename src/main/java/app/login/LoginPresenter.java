package app.login;

import app.security.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

@Component
@UIScope
class LoginPresenter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private SessionAuthenticationStrategy sessionAuthenticationStrategy;

    void view (LoginView view){
        view.setEnabled(true);

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            view.getUI().ifPresent(ui -> ui.navigate(""));
        }


        view.addLoginListener( e -> {
            final Authentication auth = new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword());
            try {
                final Authentication authenticated = authenticationProvider.authenticate(auth);
                SecurityContextHolder.getContext().setAuthentication(authenticated);

                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                UI.getCurrent().navigate("admin");


            } catch (AuthenticationException e1){
                view.setError(true);
            }
        });

    }
}

