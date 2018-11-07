package com.zut.galaweb.galaconfig;

import com.zut.galaweb.dto.GalaConfig;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/config/")
@Slf4j
@CrossOrigin
public class GalaConfigController {

    @Autowired
    private GalaConfigCache configCache;

    @Data
    @Builder
    private static class ConfigEntry {
        private String key;
        private String value;
    }

    private enum ModeConfigType {
        AUCTION_MODE(adaptBool(GalaConfig::isAuctionModeEnabled), adaptBool(GalaConfig::setAuctionModeEnabled)),
        TARGET_TICKER_MODE(adaptBool(GalaConfig::isTargetTickerEnabled), adaptBool(GalaConfig::setTargetTickerEnabled)),
        TOTAL_ON_NIGHT_MODE(adaptBool(GalaConfig::isTotalOnTheNightEnabled), adaptBool(GalaConfig::setTotalOnTheNightEnabled)),
        LAST_DONATION_MODE(adaptBool(GalaConfig::isLastDonationEnabled), adaptBool(GalaConfig::setLastDonationEnabled)),
        MESSAGE_MODE(adaptBool(GalaConfig::isMessageEnabled), adaptBool(GalaConfig::setMessageEnabled)),
        INSTA_MODE(adaptBool(GalaConfig::isInstaEnabled), adaptBool(GalaConfig::setInstaEnabled)),
        AVA_INFO_MODE(adaptBool(GalaConfig::isAvaInfoEnabled), adaptBool(GalaConfig::setAvaInfoEnabled));

        private Function<GalaConfig, String> valueExtractor;
        private BiConsumer<GalaConfig, String> setter;

        ModeConfigType(Function<GalaConfig, String> valueExtractor, BiConsumer<GalaConfig, String> setter) {
            this.valueExtractor = valueExtractor;
            this.setter = setter;
        }

        public ConfigEntry buildConfigEntry(GalaConfig config) {
            return ConfigEntry.builder()
                    .key(this.name())
                    .value(valueExtractor.apply(config))
                    .build();
        }

        static private Function<GalaConfig, String> adaptBool(Function<GalaConfig, Boolean> valueExtractor) {
            return config -> valueExtractor.apply(config).toString();
        }

        static private BiConsumer<GalaConfig, String> adaptBool(BiConsumer<GalaConfig, Boolean> valueExtractor) {
            return (config, boolAsString) -> valueExtractor.accept(config, Boolean.parseBoolean(boolAsString));
        }

        static public List<ConfigEntry> extractEntriesFromGalaConfig(GalaConfig config) {
            return Lists.mutable.of(ModeConfigType.values())
                    .collect(x -> x.buildConfigEntry(config));

        }

        static public void updateGalaConfig(GalaConfig config, String key, String value) {
            ModeConfigType.valueOf(key).setter.accept(config, value);
        }
    }
    private enum ConfigType {
        INSTA_TAG(GalaConfig::getInstaTag, GalaConfig::setInstaTag),
        MESSAGE(GalaConfig::getMessage, GalaConfig::setMessage),
        STARTING_TOTAL(adaptInt(GalaConfig::getStartingTotal), adaptInt(GalaConfig::setStartingTotal)),
        STARTING_DONATIONS(adaptInt(GalaConfig::getStartingDonations), adaptInt(GalaConfig::setStartingDonations)),
        TOTAL_DONATIONS(adaptInt(GalaConfig::getTotalDonations), adaptInt(GalaConfig::setTotalDonations)),
        LOW_TARGET_NAME(GalaConfig::getLowTargetName, GalaConfig::setLowTargetName),
        LOW_TARGET_AMOUNT(adaptInt(GalaConfig::getLowTargetAmount), adaptInt(GalaConfig::setLowTargetAmount)),
        HIGH_TARGET_NAME(GalaConfig::getHighTargetName, GalaConfig::setHighTargetName),
        HIGH_TARGET_AMOUNT(adaptInt(GalaConfig::getHighTargetAmount), adaptInt(GalaConfig::setHighTargetAmount));

        private Function<GalaConfig, String> valueExtractor;
        private BiConsumer<GalaConfig, String> setter;

        ConfigType(Function<GalaConfig, String> valueExtractor, BiConsumer<GalaConfig, String> setter) {
            this.valueExtractor = valueExtractor;
            this.setter = setter;
        }

        public ConfigEntry buildConfigEntry(GalaConfig config) {
            return ConfigEntry.builder()
                    .key(this.name())
                    .value(valueExtractor.apply(config))
                    .build();
        }

        static private Function<GalaConfig, String> adaptInt(Function<GalaConfig, Integer> valueExtractor) {
            return config -> valueExtractor.apply(config).toString();
        }

        static private BiConsumer<GalaConfig, String> adaptInt(BiConsumer<GalaConfig, Integer> valueExtractor) {
            return (config, intAsString) -> valueExtractor.accept(config, Integer.parseInt(intAsString));
        }

        static public List<ConfigEntry> extractEntriesFromGalaConfig(GalaConfig config) {
            return Lists.mutable.of(ConfigType.values())
                    .collect(x -> x.buildConfigEntry(config));

        }

        static public void updateGalaConfig(GalaConfig config, String key, String value) {
            ConfigType.valueOf(key).setter.accept(config, value);
        }
    }

    @GET
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<ConfigEntry> getAllConfig() {
        return ConfigType.extractEntriesFromGalaConfig(configCache.get());
    }

    @PUT
    @Path("/{key}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void update(@PathParam("key") String key, @FormParam("value") String value) {
        GalaConfig galaConfig = configCache.get();
        ConfigType.updateGalaConfig(galaConfig, key, value);
    }

    @GET
    @Path("/mode")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<ConfigEntry> getAllModes() {
        return ModeConfigType.extractEntriesFromGalaConfig(configCache.get());
    }

    @PUT
    @Path("/mode/{key}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateMode(@PathParam("key") String key, @FormParam("value") String value) {
        GalaConfig galaConfig = configCache.get();
        ModeConfigType.updateGalaConfig(galaConfig, key, value);
    }

}
