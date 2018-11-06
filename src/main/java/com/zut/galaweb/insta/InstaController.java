package com.zut.galaweb.insta;

import com.zut.galaweb.dto.InstaPost;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/insta/")
@Slf4j
@CrossOrigin
public class InstaController {

    @Autowired
    private InstaCache instaCache;

    @GET
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<InstaPost> getAllPosts() {
        return instaCache.getAllPosts();
    }

    @GET
    @Path("/pending")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<InstaPost> getPendingPosts() {
        MutableList<String> approved = instaCache.getApproved();
        MutableList<String> rejected = instaCache.getRejected();
        return instaCache.getAllPosts()
                .select(x -> !approved.contains(x.getId()) && !rejected.contains(x.getId()));
    }

    @GET
    @Path("/approved")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<String> getApprovedIds() {
        return instaCache.getApproved();
    }

    @GET
    @Path("/rejected")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<String> getRejectedIds() {
        return instaCache.getRejected();
    }

    @PUT
    @Path("/reset")
    @CrossOrigin
    public void reset() {
        instaCache.reset();
    }

    @PUT
    @Path("/approve/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void approve(@PathParam("id") String id) {
        log.info("Received approve request for post id: [{}]", id);
        instaCache.approvePost(id);
    }

    @DELETE
    @Path("/reject/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void reject(@PathParam("id") String id) {
        log.info("Received reject request for post id: [{}]", id);
        instaCache.rejectPost(id);
    }


}
