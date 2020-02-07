package app.content;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.InputStream;

@Controller
@RequestMapping ("files")
@AllArgsConstructor
public class DownloadController {
    private final StorageServiceImpl service;
    @GetMapping("{name}")
    public ResponseEntity<InputStreamResource> getAsInputStreamResource(@PathVariable String name) {
        try {
            return service.getFileAsInputStreamResource(name);
        }
        catch (FileNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
