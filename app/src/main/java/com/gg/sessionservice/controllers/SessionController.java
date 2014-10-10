package com.gg.sessionservice.controllers;

import com.gg.sessionservice.api.Session;
import com.gg.sessionservice.repository.SessionRepository;
import com.google.common.base.Optional;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.valueOf;

@Path("/session")
@AllArgsConstructor
public class SessionController {
    private SessionRepository sessionRepository;

    @GET
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Session> get(@PathParam("sessionId") String sessionId, @DefaultValue("0") @QueryParam("ttl") Long ttl) {
        Optional<Map<String, String>> sessionData = sessionRepository.getSessionData(sessionId, ttl);
        return sessionData.transform(data -> new Session(sessionId, ttl, data));
    }

    @POST
    @Consumes("application/json")
    public String create(Session session) {
        Map<String, String> sessionData = session.getData();
        sessionData.put("created", valueOf(new Date().getTime()));
        return sessionRepository.create(new Session(UUID.randomUUID().toString(), session.getTtl(), sessionData));
    }

    @PUT
    @Consumes("application/json")
    public void update(Session session) {
        sessionRepository.update(session);
    }

    @POST
    @Consumes("application/json")
    @Path("/{sessionId}/touch")
    public void touch(@PathParam("sessionId") String sessionId, Long ttl) {
        sessionRepository.updateTtl(sessionId, ttl);
    }
}
