package com.zut.galaweb.insta;

import com.zut.galaweb.dto.InstaPost;
import com.zut.galaweb.galaconfig.GalaConfigCache;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

@Component
@Slf4j
public class InstagramScraper {

    @Autowired
    private GalaConfigCache configCache;

    @Autowired
    private InstaCache cache;

    @Scheduled(fixedRate = 90040)
    public void pollDonations() throws IOException {
        String instaTag = configCache.getInstaTag();
        log.info("Polling instagram posts with tag [{}]...", instaTag);
        Document doc = Jsoup.connect("https://www.instagram.com/explore/tags/" + instaTag + "/?__a=1")
                .ignoreContentType(true)
                .userAgent("Mozilla")
                .timeout(5000)
                .get();

        String jsonString = doc.select("body").text();
        JSONObject jsonObj = new JSONObject(jsonString);
        JSONArray recentPosts = jsonObj
                .getJSONObject("graphql")
                .getJSONObject("hashtag")
                .getJSONObject("edge_hashtag_to_media")
                .getJSONArray("edges");

        Iterator var2 = recentPosts.iterator();

        while (true) {
            while (var2.hasNext()) {
                Object element = var2.next();
                if (element != null && !JSONObject.NULL.equals(element)) {
                    if (element instanceof JSONObject) {
                        processRecentPost((JSONObject) element);
                    }
                }
            }
            log.info("finished parsing recent instagram posts");
            return;
        }
    }

    private void processRecentPost(JSONObject element) {
        try {
            JSONObject node = element.getJSONObject("node");
            log.info("recent post: {}", node);
            if (node.getBoolean("is_video")) {
                log.info("Skipping video post");
                return;
            }
            cache.addPost(
                    InstaPost.builder()
                            .id(node.getString("id"))
                            .owner(node.getJSONObject("owner").getString("id"))
                            .imageUrl(node.getString("display_url"))
                            .thumbnailUrl(node.getString("thumbnail_src"))
                            .takenAt(node.getLong("taken_at_timestamp"))
                            .build()
            );
        } catch (Exception e) {
            log.warn("Error parsing post. Skipping: " + element, e);
        }
    }
}
