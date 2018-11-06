package com.zut.galaweb.galaconfig;

import com.zut.galaweb.dto.GalaConfig;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class GalaConfigCache {

    private final AtomicReference<GalaConfig> galaConfig = new AtomicReference<>(GalaConfig.builder().build());

    public GalaConfig get() {
        return galaConfig.get();
    }

    public String getInstaTag() {
        return galaConfig.get().getInstaTag();
    }

    public GalaConfig updateInstaTag(String tag) {
        GalaConfig config = galaConfig.get();
        config.setInstaTag(tag);
        return config;
    }

    public GalaConfig updateMode(GalaModes mode, Boolean isEnabled) {
        GalaConfig config = galaConfig.get();
        mode.updateGalaConfig(config, isEnabled);
        return config;
    }
}
