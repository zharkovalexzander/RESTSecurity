package springapp.security;

public class UserSession {
    private long lifeTime;
    private long lifeTimeMax;
    private String token;
    private SessionCredentials sessionCredentials;

    public UserSession(long lifeTimeMax) {
        this.lifeTimeMax = lifeTimeMax;
        this.lifeTime = System.currentTimeMillis() + lifeTimeMax;
        this.sessionCredentials = new SessionCredentials();
    }

    public UserSession updateLifeTime() {
        this.lifeTime += lifeTimeMax;
        return this;
    }

    public UserSession addCredentials(SessionCredentials sessionCredentials) {
        this.sessionCredentials.merge(sessionCredentials);
        return this;
    }

    public boolean isStillAlive() {
        return (System.currentTimeMillis() < this.lifeTime);
    }

    public UserSession setToken(String token) {
        this.token = token;
        return this;
    }

    public SessionCredentials getCredentials() {
        return this.sessionCredentials;
    }

    public boolean tokenEquals(String token) {
        System.out.println("a:token " + this.token + " : " + token);
        return this.token.equals(token);
    }
}
