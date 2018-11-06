package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FundraiseAmount {

    @Builder.Default
    private final String id = UUID.randomUUID().toString();

    private String name;

    private String category;

    private int amount;
}
