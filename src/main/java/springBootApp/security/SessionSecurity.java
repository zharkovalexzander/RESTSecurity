package springBootApp.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import springBootApp.security.encryption.Encryption;
import springBootApp.security.stereotype.CheckLifetime;

import java.util.LinkedList;
import java.util.List;

@Component
@Aspect
public class SessionSecurity {
    private static SessionSecurity ourInstance = new SessionSecurity();

    private long lifeTime;
    private List<UserSession> userSessionsList;

    private SessionSecurity() {
        this.userSessionsList = new LinkedList<>();
        this.lifeTime = 1800000;
    }

    public static SessionSecurity getInstance() {
        return ourInstance;
    }

    public void setLifeTime(long lifeTime, TimeQualifier timeQualifier) {
        this.lifeTime = lifeTime
                * timeQualifier.getQualifier();
    }

    @CheckLifetime
    public String newSession(SessionCredentials sessionCredentials) {
        String token = Encryption.oneStepEncryption();
        UserSession userSession = new UserSession(lifeTime)
                .setToken(token).addCredentials(sessionCredentials);
        this.userSessionsList.add(userSession);
        return token;
    }

    @CheckLifetime
    public boolean sessionExists(String token) {
        for (UserSession userSession : this.userSessionsList) {
            if (userSession.tokenEquals(token)) {
                return true;
            }
        }
        return false;
    }

    @CheckLifetime
    public boolean credentialsArePresent(SessionCredentials sessionCredentials) {
        for (UserSession userSession : this.userSessionsList) {
            if (userSession.getCredentials().exists(sessionCredentials)) {
                return true;
            }
        }
        return false;
    }

    @CheckLifetime
    public boolean removeSession(String token) {
        System.out.println("a:Size" + this.userSessionsList.size() + "\n");
        if (sessionExists(token)) {
            System.out.println("a:Token Exists\n");
            for (UserSession userSession : this.userSessionsList) {
                if (userSession.tokenEquals(token)) {
                    System.out.println("a:Token Found\n");
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

    @Before("execution (@springBootApp.security.stereotype.CheckLifetime * *(..))")
    public void removeOld() {
        for (UserSession session : this.userSessionsList) {
            System.out.println("alive: " + session.isStillAlive());
            if (!session.isStillAlive()) {
                this.userSessionsList.remove(session);
            }
        }
    }


}
