package com.zut.galaweb;

import com.zut.galaweb.dto.FundraiseAmount;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/fundraise/")
@Slf4j
@CrossOrigin
public class FundraiseController {

    @GET
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<FundraiseAmount> getFundRaisingState() {
        return Lists.mutable.of(
                FundraiseAmount.builder().amount(100).category("XY").name("fds").build(),
                FundraiseAmount.builder().amount(100).category("XY").name("fds").build(),
                FundraiseAmount.builder().amount(100).category("XY").name("fds").build()
        );
    }

}
