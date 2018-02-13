package springBootApp.security;

import java.util.*;

public class SessionCredentials {
    private List<Map.Entry<String, String>> credentials;

    public SessionCredentials() {
        this.credentials = new LinkedList<>();
    }

    public void merge(SessionCredentials sessionCredentials) {
        this.credentials.addAll(sessionCredentials.credentials);
    }

    public void newCredential(String key, String value) {
        if (!hasKeyWithValue(key, value)) {
            this.credentials.add(new AbstractMap.SimpleEntry<>(key, value));
        }
    }

    public boolean exists(SessionCredentials sessionCredentials) {
        for (Map.Entry<String, String> entry : sessionCredentials.credentials) {
            if (hasKeyWithValue(entry.getKey(), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasKeyWithValue(String key, String value) {
        for (Map.Entry<String, String> entry : this.credentials) {
            if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
