package app.api;

import app.api.services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@Validated
@RequestMapping ("files")
@AllArgsConstructor
public class DownloadController {
    private final StorageService service;
    @GetMapping(value = "{name:.+}")
    public ResponseEntity<InputStreamResource> getAsInputStreamResource(@PathVariable String name) {
        try {
            return service.getFileAsInputStreamResource(name);
        }
        catch (FileNotFoundException e){
            System.out.println("Not found");
            return ResponseEntity.notFound().build();
        }
    }
}