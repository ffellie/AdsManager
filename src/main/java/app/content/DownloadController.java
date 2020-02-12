package app.content;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

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