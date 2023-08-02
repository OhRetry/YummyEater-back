package com.YammyEater.demo.service.upload;

import org.springframework.web.multipart.MultipartFile;

/*
서로 다른 여러 서버에 자원을 업로드하는 방법을 추상화하는 인터페이스
자원을 KEY로 관리한다.
 */
public interface ResourceUploadService {
    String uploadResource(MultipartFile resource);
    String getResourceKeyFromURL(String resourceURL);
    void deleteResourceByKey(String key);
}
