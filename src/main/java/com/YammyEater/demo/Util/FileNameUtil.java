package com.YammyEater.demo.Util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class FileNameUtil {

    public MediaType getMediaTypeByFilename(String filename) {
        int extIdx = filename.lastIndexOf(".");

        String ext = "";
        if(extIdx > 0) {
            ext = filename.substring(extIdx + 1);
        }
        return getMediaTypeByExt(ext);
    }
    public MediaType getMediaTypeByExt(String ext) {
        if(ext.toLowerCase().equals("png")) {
            return MediaType.IMAGE_PNG;
        }
        else if(ext.toLowerCase().equals("jpg")) {
            return MediaType.IMAGE_JPEG;
        }
        else if(ext.toLowerCase().equals("gif")) {
            return MediaType.IMAGE_GIF;
        }
        return null;
    }
}
