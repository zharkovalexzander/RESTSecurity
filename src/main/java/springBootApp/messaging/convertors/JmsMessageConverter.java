package springBootApp.messaging.convertors;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import springBootApp.messaging.templates.Ticket;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

public class JmsMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, Session session) throws JMSException, MessageConversionException {
        Ticket ticket = (Ticket) o;
        MapMessage message = session.createMapMessage();
        message.setString("topic", ticket.getTopic());
        message.setString("description", ticket.getDescription());
        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        return new Ticket(mapMessage.getString("topic"),
                mapMessage.getString("description"));
    }

    public String formatMessage(Object o) {
        Ticket ticket = (Ticket) o;
        return ticket.toString();
    }
}
