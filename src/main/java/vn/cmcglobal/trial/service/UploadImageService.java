package vn.cmcglobal.trial.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImageService {
    @Value("${app.upload.path}")
    private String uploadPath;

    /**
     * init create dir
     *
     * @param prefix folder upload
     */
    public void init(String prefix){
        try {
            Files.createDirectories(Paths.get(uploadPath + prefix));
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file, String prefix) {
        uploadFile(file, prefix);
    }

    /**
     * handle upload file
     *
     * @param file content file
     * @param prefix folder upload
     */
    private void uploadFile(MultipartFile file, String prefix) {
        try {
            init(prefix);

            Path path = Paths.get(uploadPath + prefix);

            System.out.println("UploadImageService.uploadFile"+ path);
            Path targetLocation = path.resolve(file.getOriginalFilename());

            if (!Files.exists(targetLocation)) {
                Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

}
