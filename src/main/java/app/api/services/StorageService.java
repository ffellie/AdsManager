package app.api.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public interface StorageService {
    ResponseEntity<InputStreamResource> getFileAsInputStreamResource (String name) throws FileNotFoundException;
}
