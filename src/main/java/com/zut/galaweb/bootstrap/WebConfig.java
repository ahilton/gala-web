package com.zut.galaweb.bootstrap;

import com.zut.galaweb.FundraiseController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig extends ResourceConfig {

    @Autowired
    public WebConfig() {
        register(FundraiseController.class);
        register(CORSFilter.class);
    }
}
