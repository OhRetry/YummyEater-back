package com.YammyEater.demo.dto.upload;

import lombok.Builder;

@Builder
public record UploadResponse(
        String resourceURL,
        String resourceKey
) {
}
