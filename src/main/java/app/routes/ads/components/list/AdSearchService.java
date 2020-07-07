package app.routes.ads.components.list;

import app.components.search.SearchService;
import app.data.ad.Ad;
import app.data.ad.AdServiceImpl;
import app.data.user.User;
import app.data.user.UserRepository;
import com.vaadin.flow.component.UI;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdSearchService implements SearchService {
    private final AdServiceImpl adService;

    private final UserRepository userRepository;

    public List<Ad> find(int offset, int limit, String param, String filter, Map<String, Boolean> sortOrders) {

        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (secUser == null) {
            UI.getCurrent().navigate("login");
        }

        User user = userRepository.findByName(secUser.getName()).orElse(null);
        if (user == null) {
            UI.getCurrent().navigate("login");
        }

        if (param != null && !param.trim().isEmpty())
            return adService.findByUserAndName(offset, limit,user, param, sortOrders);
        return adService.findAll(offset, limit, sortOrders, user);
    }

    public int count(String name, String filter) {

        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (secUser == null) {
            UI.getCurrent().navigate("login");
        }

        User user = userRepository.findByName(secUser.getName()).orElse(null);
        if (user == null) {
            UI.getCurrent().navigate("login");
        }

        if (name != null && !name.trim().isEmpty())
            return adService.countByUserAndName(user, name);

        return adService.count(user);
    }
}
