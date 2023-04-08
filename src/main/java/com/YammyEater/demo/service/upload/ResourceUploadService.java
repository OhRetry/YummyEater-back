package com.YammyEater.demo.service.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceUploadService {
    String uploadResource(MultipartFile resource);
    Resource getResource(String resourcePath);
}
