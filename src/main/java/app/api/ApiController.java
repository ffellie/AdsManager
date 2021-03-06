package app.api;

import app.api.models.GroupModel;
import app.api.services.GroupApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class ApiController {
    private final GroupApiService groupApiService;
    @GetMapping()
    public ResponseEntity<GroupModel> getGroup (@RequestParam String url){return groupApiService.getGroup(url);}
}
