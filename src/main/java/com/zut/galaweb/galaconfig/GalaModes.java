package com.zut.galaweb.galaconfig;

import com.zut.galaweb.dto.GalaConfig;

import java.util.function.BiConsumer;

public enum GalaModes {

    AUCTION(GalaConfig::setAuctionModeEnabled),
    STATIC(GalaConfig::setAuctionModeEnabled),
//    TARGET_TICKER(GalaConfig::setTargetTickerEnabled),
    TOTAL_ON_NIGHT(GalaConfig::setTotalOnTheNightEnabled),
    LAST_DONATION(GalaConfig::setLastDonationEnabled),
    MESSAGE(GalaConfig::setMessageEnabled),
    INSTA(GalaConfig::setInstaEnabled),
    AVA_INFO(GalaConfig::setAvaInfoEnabled),
    INFO_AUCTION(GalaConfig::setInfoAuctionEnabled),
    FACTS(GalaConfig::setFactsEnabled),
    SPONSORS(GalaConfig::setSponsorsEnabled);

    private BiConsumer<GalaConfig, Boolean> galaConfigSetter;

    GalaModes(BiConsumer<GalaConfig, Boolean> galaConfigSetter) {
        this.galaConfigSetter = galaConfigSetter;
    }

    public void updateGalaConfig(GalaConfig config, Boolean enabled){
        galaConfigSetter.accept(config, enabled);
    }
}
