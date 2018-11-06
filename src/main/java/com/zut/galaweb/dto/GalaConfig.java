package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GalaConfig {

    @Builder.Default
    private String instaTag = "avasjourney";

    @Builder.Default
    private String message = "We love you Ava!";

    // Total raised on the night =
    //    starting total +
    //    (total donations - starting donations) +
    //    [sum of all fundraising amounts]
    @Builder.Default
    private int startingTotal = 220_000;

    // Go fund me amounts
    @Builder.Default
    private int startingDonations = 200_000;
    @Builder.Default
    private int totalDonations = 230_000;

    // Target (in AUD)
    @Builder.Default
    private String lowTargetName = "Cost of Vaccine";
    @Builder.Default
    private String highTargetName = "NYC Relocation";
    @Builder.Default
    private int lowTargetAmount = 300_000;
    @Builder.Default
    private int highTargetAmount = 350_000;

    // Modes
    @Builder.Default
    private boolean auctionModeEnabled = false;
    @Builder.Default
    private boolean targetTickerEnabled = true;
    @Builder.Default
    private boolean totalOnTheNightEnabled = true;
    @Builder.Default
    private boolean lastDonationEnabled = false;
    @Builder.Default
    private boolean messageEnabled = false;
    @Builder.Default
    private boolean instaEnabled = true;
    @Builder.Default
    private boolean avaInfoEnabled = true;

}
