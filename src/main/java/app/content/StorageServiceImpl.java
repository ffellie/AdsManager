package app.content;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;


@Service
public class StorageServiceImpl {
    public ResponseEntity<InputStreamResource> getFileAsInputStreamResource(String name) throws FileNotFoundException {
        try {
            Resource resource = new UrlResource(new URI("file:///Users/dias/projects/ad-content/" + name));

            if (resource.exists()) {

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFile().getName());

                return ResponseEntity.ok()
                        .headers(httpHeaders)
                        .contentLength(resource.getFile().length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(new InputStreamResource(resource.getInputStream()));
            } else {
                throw new FileNotFoundException("File not found : " + name);
            }
        } catch (Exception ex) {
            throw new FileNotFoundException("File not found : " + name);
        }
    }
}
