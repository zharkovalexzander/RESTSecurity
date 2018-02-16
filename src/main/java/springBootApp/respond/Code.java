package springBootApp.respond;

public enum Code {
    AUTHORIZED("20"), SUCCESS("21"), NO_CONTENT_AVAILABLE("22"),
    INVALID_INPUT_DATA("30");

    private String equivalence;

    Code(String equivalence) {
        this.equivalence = equivalence;
    }

    public String getEquivalence() {
        return equivalence;
    }
}
