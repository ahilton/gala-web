package com.zut.galaweb.insta;

import com.zut.galaweb.dto.InstaPost;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.utility.Iterate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class InstaCache {

    private final List<InstaPost> allPosts = new CopyOnWriteArrayList<>();

    private final List<String> approvedPosts = new CopyOnWriteArrayList<>();
    private final List<String> rejectedPosts = new CopyOnWriteArrayList<>();

    public void addPost(InstaPost post) {
        if (!allPosts.contains(post)) {
            allPosts.add(post);
        }
        printCacheState();
    }

    public void approvePost(InstaPost post) {
        rejectedPosts.remove(post.getId());
        if (!approvedPosts.contains(post.getId())) {
            approvedPosts.add(post.getId());
        }
        printCacheState();
    }

    public void rejectPost(InstaPost post) {
        approvedPosts.remove(post.getId());
        if (!rejectedPosts.contains(post.getId())) {
            rejectedPosts.add(post.getId());
        }
        printCacheState();
    }

    public List<InstaPost> getAllPosts() {
        return Lists.mutable.ofAll(allPosts);
    }

    public MutableList<String> getApproved() {
        return Lists.mutable.ofAll(approvedPosts);
    }

    public MutableList<String> getRejected() {
        return Lists.mutable.ofAll(rejectedPosts);
    }

    private void printCacheState() {
        log.info("ALL INSTA POSTS:");
        log.info(Iterate.makeString(allPosts));
        log.info("APPROVED POSTS:");
        log.info(Iterate.makeString(approvedPosts));
        log.info("REJECTED POSTS:");
        log.info(Iterate.makeString(rejectedPosts));
    }

}
