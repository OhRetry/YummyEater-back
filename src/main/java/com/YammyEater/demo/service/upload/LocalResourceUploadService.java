package com.YammyEater.demo.service.upload;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.exception.upload.ResourceDownloadException;
import com.YammyEater.demo.exception.upload.ResourceUploadException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/*
서버의 파일 시스템에 업로드하는 서비스
 */
@Service
public class LocalResourceUploadService implements ResourceUploadService {

    @Value("${upload.local.upload_root}")
    private String UPLOAD_ROOT;

    @Value("${upload.local.resource_host}")
    private String RESOURCE_HOST;

    @Value("${upload.local.urlPath}")
    private String URL_PATH;

    @Override
    public String uploadResource(MultipartFile resource) {
        String filename = createFileName(resource.getOriginalFilename());
        File saveTo = new File(getRealPath(filename));
        try {
            resource.transferTo(saveTo);
        }
        catch (IOException e) {
            throw new ResourceUploadException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return getWebPath(filename);
    }

    @Override
    public Resource getResource(String resourcePath) {
        File file = new File(getRealPath(resourcePath));
        Resource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            throw new ResourceDownloadException(ErrorCode.NOT_FOUND);
        }
        return resource;
    }

    private String getRealPath(String resourcePath) {
        return UPLOAD_ROOT + resourcePath;
    }

    private String getWebPath(String resourcePath) {
        return RESOURCE_HOST + URL_PATH + resourcePath;
    }

    private String createFileName(String uploadedName) {
        String ext = uploadedName.substring(uploadedName.lastIndexOf("."));
        return UUID.randomUUID().toString() + ext;
    }
}
