package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstaPost {

    private String id;
    private String thumbnailUrl;
    private String imageUrl;
    private String owner;
    private Long takenAt;
}
