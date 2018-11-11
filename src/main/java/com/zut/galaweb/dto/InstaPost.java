package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstaPost {

    private String id;
    private transient String thumbnailUrl;
    private transient String imageUrl;
    private transient String owner;
    private transient Long takenAt;
}
