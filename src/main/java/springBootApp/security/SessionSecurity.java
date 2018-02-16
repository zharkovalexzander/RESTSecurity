package springBootApp.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import springBootApp.messaging.senders.JmsMessageSender;
import springBootApp.messaging.templates.Ticket;
import springBootApp.security.encryption.Encryption;
import springBootApp.security.stereotype.CheckLifetime;

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
        String token = Encryption.oneStepEncryption();
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


}
