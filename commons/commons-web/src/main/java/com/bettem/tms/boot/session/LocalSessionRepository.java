package com.bettem.tms.boot.session;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.Session;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author GaoFans
 */
public class LocalSessionRepository implements FindByIndexNameSessionRepository<MapSession> {


    private Integer defaultMaxInactiveInterval;
    private final Map<String, Session> sessions;

    public LocalSessionRepository(Map<String, Session> sessions) {
        if (sessions == null) {
            throw new IllegalArgumentException("sessions cannot be null");
        } else {
            this.sessions = sessions;
        }
    }

    @Override
    public Map<String, MapSession> findByIndexNameAndIndexValue(String indexName, String indexValue) {
        Map<String, MapSession> map = Collections.emptyMap();
        if (PRINCIPAL_NAME_INDEX_NAME.equals(indexName)) {
            //根据指定的属性查找session
            //TODO 现在是遍历效率低
            sessions
                    .entrySet()
                    .stream()
                    .filter(entry -> entry
                            .getValue()
                            .getAttribute(indexName)
                            .equals(indexValue)).collect(Collectors.toSet())
                    .forEach(entry -> {
                        map.put(entry.getKey(), (MapSession) entry.getValue());
                    });
        }
        return map;
    }

    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    @Override
    public void save(MapSession session) {
        if (!session.getId().equals(session.getOriginalId())) {
            this.sessions.remove(session.getOriginalId());
        }

        this.sessions.put(session.getId(), new MapSession(session));
    }

    @Override
    public MapSession findById(String id) {
        Session saved = this.sessions.get(id);
        if (saved == null) {
            return null;
        } else if (saved.isExpired()) {
            this.deleteById(saved.getId());
            return null;
        } else {
            return new MapSession(saved);
        }
    }

    @Override
    public void deleteById(String id) {
        this.sessions.remove(id);
    }

    @Override
    public MapSession createSession() {
        MapSession result = new MapSession();
        if (this.defaultMaxInactiveInterval != null) {
            result.setMaxInactiveInterval(Duration.ofSeconds((long)this.defaultMaxInactiveInterval));
        }

        return result;
    }
}
