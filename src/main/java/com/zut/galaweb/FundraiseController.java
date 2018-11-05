package com.zut.galaweb;

import com.zut.galaweb.dto.FundraiseAmount;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/fundraise/")
@Slf4j
@CrossOrigin
public class FundraiseController {

    private final MutableList<FundraiseAmount> amounts = Lists.mutable.of(
            FundraiseAmount.builder().amount(100).category("XY").name("fds").build(),
            FundraiseAmount.builder().amount(100).category("XY").name("fds").build(),
            FundraiseAmount.builder().amount(100).category("XY").name("fds").build()
    );

    @GET
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<FundraiseAmount> getFundRaisingState() {
        return amounts;
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void deleteFundRaisingState(
            @PathParam("id") String id,
            @FormParam("name") String name,
            @FormParam("category") String category,
            @FormParam("amount") int amount
    ) {
        log.info("Received delete request for id: [{}]", id);
        FundraiseAmount toDelete = FundraiseAmount.builder().amount(amount).category(category).name(name).build();
        log.info("Params: {}", toDelete);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateFundRaisingState(
            @PathParam("id") String id,
            @FormParam("name") String name,
            @FormParam("category") String category,
            @FormParam("amount") int amount
    ) {
        log.info("Received update request for id: [{}]", id);
        FundraiseAmount toUpdate = FundraiseAmount.builder().amount(amount).category(category).name(name).build();
        log.info("Params: {}", toUpdate);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public CreationResponse createFundRaisingState(
            @FormParam("name") String name,
            @FormParam("category") String category,
            @FormParam("amount") int amount
    ) {
        FundraiseAmount newObj = FundraiseAmount.builder().amount(amount).category(category).name(name).build();
        log.info("Received create request: [{}]", newObj);
        amounts.add(newObj);
        return CreationResponse.builder().data(Lists.mutable.of(newObj)).build();
    }

    @Data
    @Builder
    private static class CreationResponse {
        private List<FundraiseAmount> data;
    }

}
