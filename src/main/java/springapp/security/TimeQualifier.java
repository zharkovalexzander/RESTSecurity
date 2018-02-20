package springapp.security;

public enum TimeQualifier {
    MILLIS(1), SECONDS(1000), MINUTES(60000);

    private long qualifier;

    TimeQualifier(long qualifier) {
        this.qualifier = qualifier;
    }

    public long getQualifier() {
        return qualifier;
    }

}
