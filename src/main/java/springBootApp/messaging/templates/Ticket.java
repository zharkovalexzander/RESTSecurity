package springBootApp.messaging.templates;

public class Ticket {
    private String topic;
    private String description;

    public Ticket(String topic, String description) {
        this.topic = topic;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String delimeter = System.lineSeparator();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ticket issue: ")
                .append(delimeter)
                .append("Topic: ")
                .append(this.topic)
                .append(delimeter)
                .append("Body: ")
                .append(this.description);
        return stringBuilder.toString();
    }
}
