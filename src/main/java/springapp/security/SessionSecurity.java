package springapp.security;

import org.springframework.stereotype.Component;
import springapp.messaging.senders.JmsMessageSender;
import springapp.security.encryption.Encryption;
import springapp.security.stereotype.CheckLifetime;

import java.util.LinkedList;
import java.util.List;

@Component
public class SessionSecurity {
    private long lifeTime;
    private List<UserSession> userSessionsList;
    private JmsMessageSender jmsMessageSender;

    public void setJmsMessageSender(JmsMessageSender jmsMessageSender) {
        this.jmsMessageSender = jmsMessageSender;
    }

    public SessionSecurity() {
        this.userSessionsList = new LinkedList<>();
        this.lifeTime = 1800000;
    }

    public void setLifeTime(long lifeTime, TimeQualifier timeQualifier) {
        this.lifeTime = lifeTime
                * timeQualifier.getQualifier();
    }

    @CheckLifetime
    public String newSession(SessionCredentials sessionCredentials) {
        removeOld();
        String token = Encryption.INSTANCE.threeStepEncryptionWithSalt();
        UserSession userSession = new UserSession(lifeTime)
                .setToken(token).addCredentials(sessionCredentials);
        this.userSessionsList.add(userSession);
        return token;
    }

    @CheckLifetime
    public boolean sessionExists(String token) {
        removeOld();
        for (UserSession userSession : this.userSessionsList) {
            if (userSession.tokenEquals(token)) {
                return true;
            }
        }
        return false;
    }

    @CheckLifetime
    public boolean credentialsArePresent(SessionCredentials sessionCredentials) {
        removeOld();
        for (UserSession userSession : this.userSessionsList) {
            if (userSession.getCredentials().exists(sessionCredentials)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeSession(String token) {
        removeOld();
        if (sessionExists(token)) {
            for (UserSession userSession : this.userSessionsList) {
                if (userSession.tokenEquals(token)) {
                    this.userSessionsList.remove(userSession);
                    return true;
                }
            }
        }
        return false;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public void removeOld() {
        for (UserSession session : this.userSessionsList) {
            System.out.println("alive: " + session.isStillAlive());
            if (!session.isStillAlive()) {
                this.userSessionsList.remove(session);
            }
        }
    }


    public JmsMessageSender getJmsMessageSender() {
        return jmsMessageSender;
    }
}
