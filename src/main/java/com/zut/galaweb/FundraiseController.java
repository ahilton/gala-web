package com.zut.galaweb;

import com.zut.galaweb.donation.FundCache;
import com.zut.galaweb.dto.Donation;
import com.zut.galaweb.dto.FundraiseAmount;
import com.zut.galaweb.dto.GalaConfig;
import com.zut.galaweb.galaconfig.GalaConfigCache;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/fundraise/")
@Slf4j
@CrossOrigin
public class FundraiseController {

    private final List<FundraiseAmount> amounts = new CopyOnWriteArrayList<>(Lists.mutable.of(
            FundraiseAmount.builder().amount(100).category("SilentAuction").name("item1").build(),
            FundraiseAmount.builder().amount(150).category("SilentAuction").name("item2").build(),
            FundraiseAmount.builder().amount(99).category("Raffle").name("total").build(),
            FundraiseAmount.builder().amount(50).category("Auction").name("item1").build()
    ));

    public MutableList<FundraiseAmount> getAmounts(){
        return Lists.mutable.ofAll(amounts);
    }

    @Autowired
    private GalaConfigCache configCache;

    @Autowired
    private FundCache fundCache;

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
        amounts.removeIf(x -> x.getId().equals(id));
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public CreationUpdateResponse updateFundRaisingState(
            @PathParam("id") String id,
            @FormParam("name") String name,
            @FormParam("category") String category,
            @FormParam("amount") int amount
    ) {
        log.info("Received update request for id: [{}]", id);
        FundraiseAmount update = FundraiseAmount.builder().amount(amount).category(category).name(name).build();
        log.info("Params: {}", update);

        FundraiseAmount toUpdate = getAmounts().detect(x -> x.getId().equals(id));
        // TODO:: return error if not exists (share with delete code)
        log.info("Before update: {}", toUpdate);
        toUpdate.setName(update.getName());
        toUpdate.setCategory(update.getCategory());
        toUpdate.setAmount(update.getAmount());
        log.info("After update: {}", toUpdate);
        return buildResponse(toUpdate);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public CreationUpdateResponse createFundRaisingState(
            @FormParam("name") String name,
            @FormParam("category") String category,
            @FormParam("amount") int amount
    ) {
        FundraiseAmount newObj = FundraiseAmount.builder().amount(amount).category(category).name(name).build();
        log.info("Received create request: [{}]", newObj);
        amounts.add(newObj);
        return buildResponse(newObj);
    }

    private CreationUpdateResponse buildResponse(FundraiseAmount newObj) {
        return CreationUpdateResponse.builder().data(Lists.mutable.of(newObj)).build();
    }

    @Data
    @Builder
    private static class CreationUpdateResponse {
        private List<FundraiseAmount> data;
    }


    @Data
    @Builder
    private static class TickerState {
        private Long total;
        private Long night;
    }

    @GET
    @Path("/ticker")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public TickerState getTickerState() {

        // Total raised on night =
        //(total donations - starting donations) +
        //    [sum of all fundraising amounts]
        long sumFromTonight = Lists.mutable.ofAll(amounts)
                .collectInt(FundraiseAmount::getAmount)
                .sum();

        GalaConfig galaConfig = configCache.get();

        int donationsTonight = galaConfig.getTotalDonations() - galaConfig.getStartingDonations();

        long tonightsTotal = sumFromTonight + donationsTonight;
        return TickerState.builder()
                .night(tonightsTotal)
                .total(tonightsTotal + galaConfig.getStartingTotal())
                .build();
    }

    // Total raised on the night =
    //    starting total +
    //    (total donations - starting donations) +
    //    [sum of all fundraising amounts]

    @GET
    @Path("/donations")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<Donation> getDonations() {
        return fundCache.getDonations();
    }
}
