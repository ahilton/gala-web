package com.zut.galaweb;

import com.zut.galaweb.dto.GalaConfig;
import com.zut.galaweb.galaconfig.GalaConfigCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/run")
@Slf4j
@CrossOrigin
public class RunController {

    @Autowired
    private GalaConfigCache configCache;

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/static")
    @CrossOrigin
    public boolean onStatic() {
        getConfigAndTurnModesOff();
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/entry")
    @CrossOrigin
    public boolean onEntry() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setMessageEnabled(true);
        galaConfig.setInstaEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/kerry")
    @CrossOrigin
    public boolean onKerry() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setKerryEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/mbb")
    @CrossOrigin
    public boolean onMBB() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setMbbEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/downtimeNoTicker")
    @CrossOrigin
    public boolean downTimeNoTicker() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setInstaEnabled(true);
        galaConfig.setAvaInfoEnabled(true);
        galaConfig.setFactsEnabled(true);
        galaConfig.setInfoAuctionEnabled(true);
        galaConfig.setLastDonationEnabled(true);
        galaConfig.setSponsorsEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/ticker")
    @CrossOrigin
    public boolean onTicker() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setTotalOnTheNightEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/everything")
    @CrossOrigin
    public boolean everything() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setInstaEnabled(true);
        galaConfig.setAvaInfoEnabled(true);
        galaConfig.setFactsEnabled(true);
        galaConfig.setInfoAuctionEnabled(true);
        galaConfig.setLastDonationEnabled(true);
        galaConfig.setSponsorsEnabled(true);
        galaConfig.setTotalOnTheNightEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/avaDelay")
    @CrossOrigin
    public boolean avaDelay() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setAvaInfoEnabled(true);
        galaConfig.setDelay(60);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/auction")
    @CrossOrigin
    public boolean auction() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setAuctionModeEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/postAuction")
    @CrossOrigin
    public boolean postAuction() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setInstaEnabled(true);
        galaConfig.setAvaInfoEnabled(true);
        galaConfig.setFactsEnabled(true);
        galaConfig.setLastDonationEnabled(true);
        galaConfig.setSponsorsEnabled(true);
        return true;
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/end")
    @CrossOrigin
    public boolean end() {
        GalaConfig galaConfig = getConfigAndTurnModesOff();
        galaConfig.setInstaEnabled(true);
        galaConfig.setAvaInfoEnabled(true);
        galaConfig.setTotalOnTheNightEnabled(true);
        return true;
    }

    private GalaConfig getConfigAndTurnModesOff() {
        GalaConfig galaConfig = configCache.get();
        galaConfig.allOff();
        return galaConfig;
    }

}
