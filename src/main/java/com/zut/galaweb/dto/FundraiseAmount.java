package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundraiseAmount {

    private String name;

    private String category;

    private int amount;
}
