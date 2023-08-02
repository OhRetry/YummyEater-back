package com.YammyEater.demo.controller.api.upload;

import com.YammyEater.demo.Util.FileNameUtil;
import com.YammyEater.demo.dto.ApiResponse;
import com.YammyEater.demo.dto.upload.UploadResponse;
import com.YammyEater.demo.service.upload.ResourceUploadService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class uploadController {

    private final ResourceUploadService resourceUploadService;
    private final FileNameUtil fileNameUtil;

    @PostMapping("/upload")
    public ApiResponse<UploadResponse> uploadResource(@RequestParam MultipartFile resource) {
        String resourceURL = resourceUploadService.uploadResource(resource);
        return ApiResponse.of(new UploadResponse(resourceURL));
    }

}
