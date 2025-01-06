package hello.calendar2.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    private final Map<String, String> sessionStore = new HashMap<String, String>();

    public void saveSession(String sessionId, String userId) {
        sessionStore.put(sessionId, userId);
    }

    public boolean isValidSession(String sessionId) {
        return sessionStore.containsKey(sessionId);
    }
}
