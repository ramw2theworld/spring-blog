package com.spring.blog.services.impl;

import com.spring.blog.configs.AppConstants;
import com.spring.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImageFile(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new IOException("File not selected");
        }
        Path directoryPath = Paths.get(AppConstants.FILE_PATH);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + (fileExtension.isEmpty() ? "" : "." + fileExtension);
        Path filePath = directoryPath.resolve(filename);

        file.transferTo(filePath);

        return filename;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws IOException {
        Path filePath = Paths.get(path, fileName);
        if(!Files.exists(filePath)){
            throw new FileNotFoundException("File not found: "+fileName);
        }
        return Files.newInputStream(filePath);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
