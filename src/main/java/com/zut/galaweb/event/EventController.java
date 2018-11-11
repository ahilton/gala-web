package com.zut.galaweb.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/events/")
@Slf4j
@CrossOrigin
public class EventController {

    @Data
    @Builder
    private static class Event {
        private String event;
        private long id;
    }

    @Data
    @Builder
    private static class NewEvents {
        private List<Event> newEvents;
        private long latestId;
    }

    private final List<Event> eventList = new CopyOnWriteArrayList<>();

    @GET
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public List<Event> getAllConfig() {
        return Lists.mutable.ofAll(eventList);
    }

    @GET
    @Path("/new")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public NewEvents getNewConfig(@QueryParam("lastid") Long lastId) {
        MutableList<Event> newEvents = Lists.mutable
                .ofAll(eventList)
                .select(x -> x.id > lastId);
        return NewEvents.builder()
                .newEvents(newEvents)
                .latestId(newEvents.getLast().getId())
                .build();
    }

    @GET
    @Path("/clear")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public void clearEvents() {
        eventList.clear();
    }

    @GET
    @Path("/create/{event}")
    @Produces(APPLICATION_JSON)
    @CrossOrigin
    public void addEvent(@PathParam("event") String event) {
        eventList.add(Event.builder()
                .event(event)
                .id(new Date().getTime())
                .build()
        );
    }
}
