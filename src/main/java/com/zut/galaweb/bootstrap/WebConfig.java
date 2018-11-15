package com.zut.galaweb.bootstrap;

import com.zut.galaweb.FundraiseController;
import com.zut.galaweb.RunController;
import com.zut.galaweb.event.EventController;
import com.zut.galaweb.galaconfig.GalaConfigController;
import com.zut.galaweb.insta.InstaController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig extends ResourceConfig {

    @Autowired
    public WebConfig() {
        register(FundraiseController.class);
        register(InstaController.class);
        register(GalaConfigController.class);
        register(EventController.class);
        register(RunController.class);
        register(CORSFilter.class);
    }
}
