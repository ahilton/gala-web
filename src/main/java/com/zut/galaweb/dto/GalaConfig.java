package com.zut.galaweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GalaConfig {

    @Builder.Default
    private String instaTag = "avasjourney,getavatoNYC";

    @Builder.Default
    private String message = "";

    // Total raised on the night =
    //    starting total +
    //    (total donations - starting donations) +
    //    [sum of all fundraising amounts]
    @Builder.Default
    private int startingTotal = 220_000;

    @Builder.Default
    private int delay = 15;

    // Go fund me amounts
    @Builder.Default
    private int startingDonations = 203_000;
    @Builder.Default
    private int totalDonations = 203_000;

    // Target (in AUD)
    @Builder.Default
    private String lowTargetName = "Cost of Vaccine";
    @Builder.Default
    private String highTargetName = "NYC Relocation";
    @Builder.Default
    private int lowTargetAmount = 290_000;
    @Builder.Default
    private int highTargetAmount = 300_000;

    // Modes
    @Builder.Default
    private boolean auctionModeEnabled = false;
    @Builder.Default
    private boolean staticModeEnabled = false;
//    @Builder.Default
//    private boolean targetTickerEnabled = false;
    @Builder.Default
    private boolean totalOnTheNightEnabled = false;
    @Builder.Default
    private boolean lastDonationEnabled = false;
    @Builder.Default
    private boolean messageEnabled = false;
    @Builder.Default
    private boolean infoAuctionEnabled = false;
    @Builder.Default
    private boolean factsEnabled = false;
    @Builder.Default
    private boolean sponsorsEnabled = false;
    @Builder.Default
    private boolean instaEnabled = false;
    @Builder.Default
    private boolean avaInfoEnabled = false;
    @Builder.Default
    private boolean kerryEnabled = false;
    @Builder.Default
    private boolean mbbEnabled = false;

}
