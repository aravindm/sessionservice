package com.gg.sessionservice.repository;

import com.gg.sessionservice.api.Session;
import com.gg.sessionservice.exceptions.SessionServiceException;
import com.google.common.base.Optional;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Map;

import static java.lang.String.valueOf;

@AllArgsConstructor
public class SessionRepository {
    private Template template;

    public Optional<Map<String, String>> getSessionData(String sessionId, long ttl) {
        updateTtl(sessionId, ttl);
        Map<String, String> sessionData = template.get(sessionId);
        return sessionData.isEmpty() ? Optional.absent() : Optional.of(sessionData);
    }

    public String create(Session session) {
        Map<String, String> sessionData = session.getData();
        sessionData.put("lastUpdated", valueOf(new Date().getTime()));

        boolean isSuccessful = template.create(session.getId(), sessionData);
        if (!isSuccessful) throw new SessionServiceException("Creating session data failed!");

        updateTtl(session.getId(), session.getTtl());
        return session.getId();
    }

    public String update(Session session) {
        Map<String, String> sessionData = session.getData();
        sessionData.put("lastUpdated", valueOf(new Date().getTime()));

        boolean isSuccessful = template.update(session.getId(), sessionData);
        if (!isSuccessful) throw new SessionServiceException("Updating session data failed!");

        updateTtl(session.getId(), session.getTtl());
        return session.getId();
    }

    public void updateTtl(String sessionId, long ttl) {
        if (ttl == 0) return;
        boolean isSuccessful = template.setExpiry(sessionId, ttl);
        if (!isSuccessful) throw new SessionServiceException("Updating session ttl failed!");
    }
}
