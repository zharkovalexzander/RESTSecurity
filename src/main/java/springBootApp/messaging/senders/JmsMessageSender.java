package springBootApp.messaging.senders;

import org.springframework.jms.core.JmsTemplate;
import springBootApp.messaging.convertors.JmsMessageConverter;
import springBootApp.messaging.templates.Ticket;

import javax.jms.Queue;

public class JmsMessageSender {
    private JmsTemplate jmsTemplate;
    private Queue queue;
    private JmsMessageConverter messageConverter;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setMessageConverter(JmsMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public void simpleSend() {
        this.jmsTemplate.send(queue, s -> s.createTextMessage("hello queue world"));
    }

    public void sendFormattedTicket(final Ticket ticket) {
        this.jmsTemplate.send(queue, s -> s
                .createTextMessage(this.messageConverter.formatMessage(ticket)));
    }

    public void sendMessage(final Ticket ticket) {
        this.jmsTemplate.convertAndSend(ticket);
    }
}
