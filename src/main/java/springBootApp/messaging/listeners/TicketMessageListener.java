package springBootApp.messaging.listeners;

import org.springframework.jms.core.JmsTemplate;
import springBootApp.messaging.templates.Ticket;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.Map;

public class TicketMessageListener implements MessageListener {
    private JmsTemplate jmsTemplate;
    private Queue queue;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String msg = ((TextMessage) message).getText();
                System.out.println(msg);
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Ticket receiveMessage() throws JMSException {
        Map map = (Map) this.jmsTemplate.receiveAndConvert();
        return new Ticket((String) map.get("topic"), (String) map.get("description"));
    }
}
