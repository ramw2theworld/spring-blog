package com.spring.blog.services;

import com.spring.blog.configs.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadImageFile(MultipartFile file) throws IOException;
    InputStream getResource(String path, String fileName) throws IOException;
}
