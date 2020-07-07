package app.api.services;

import app.constants.RouteURLs;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class StorageServiceImpl implements StorageService{
    public ResponseEntity<InputStreamResource> getFileAsInputStreamResource(String name) throws FileNotFoundException {
        File file = new File(RouteURLs.FILES_DIR + name);
        if (file.exists()) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            System.out.println(file.getName());
            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(new FileInputStream(file)));
        } else {
            System.out.println("1");
            throw new FileNotFoundException("File not found : " + name);
        }
    }
}
