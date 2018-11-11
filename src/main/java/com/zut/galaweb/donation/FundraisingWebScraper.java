package com.zut.galaweb.donation;

import com.zut.galaweb.dto.Donation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class FundraisingWebScraper {

    @Autowired
    private FundCache fundCache;

    @Scheduled(fixedRate = 120040)
    public void pollDonations() throws IOException {
        log.info("Polling donations...");

        Document doc = Jsoup.connect("https://www.gofundme.com/jupcnf-avas-journey")
                .userAgent("Mozilla")
                .timeout(5000)
                .get();
        log.debug(doc.toString());

//        Elements donationList = doc.select(".donations-column-contain .showcontrol-donations .supporters-list");
        Elements donationsElements = doc.select(".donations-column-contain .showcontrol-donations .supporters-list .js-donation-content");
        MutableList<Donation> donations = Lists.mutable.of();

        // extract donations
        donationsElements.forEach(el -> donations.add(convertDonation(el)));

        log.info("Retrieved {} donations", donations.size());

        int currentTotal = parseGoal(doc.select(".goal").first().text());
        fundCache.updateCurrentDonationTotal(currentTotal);

        try {
            log.info("Polling donation comments...");
            Document commsDoc = Jsoup.connect("https://www.gofundme.com/mvc.php?route=donate/pagingContentsFoundation&url=jupcnf-avas-journey&idx=0")
                    .userAgent("Mozilla")
                    .timeout(5000)
                    .referrer("https://www.gofundme.com/jupcnf-avas-journey")
                    .get();


            //commsDoc.select(".js-comment-content").first().select(".supporter-content-wrap .truncate-text").text()
            Elements commentElements = commsDoc.select(".js-comment-content");
            log.info("Retrieved {} donation comments", commentElements.size());
            commentElements.forEach(el -> {
                //String donationId = el.attr("data-id");
                String msg = el.select(".supporter-content-wrap .truncate-text").text();
                String name = el.select(".supporter-name").text();
                donations.select(d->d.getName().equals(name)).forEach(d->d.setMessage(msg));
            });
        }
        catch (Exception e){
            log.warn("Exception trying to process donation comments", e);
        }

        fundCache.updateDonations(donations);
    }

    private int parseGoal(String goalText) {
        //$58,405 of $350,000 goal
        log.info("Goal text: [{}]", goalText);
        String runningTotal = goalText.split(" ")[0];
        log.info("Running total (first element): [{}]", runningTotal);
        String cleanRunningTotal = runningTotal.replaceAll(",", "").replaceAll("\\$", "");
        log.info("cleanRunningTotal: [{}]", cleanRunningTotal);
        return Integer.parseInt(cleanRunningTotal);
    }

    private Donation convertDonation(Element el) {
        String donationId = el.attr("data-id");
        String amount = el.select(".supporter-amount").text();
        String name = el.select(".supporter-name").text();
        String time = el.select(".supporter-time").text();
        if (donationId == null || donationId.isEmpty()
                || amount == null || amount.isEmpty()
                || name == null || name.isEmpty()
                ){
            throw new RuntimeException("Unable to extract donation info from html element"+ el.toString());
        }

        return Donation.builder()
                .donationId(donationId)
                .amount(amount)
                .name(name)
                .time(time)
                .message("")
                .build();
    }
}
