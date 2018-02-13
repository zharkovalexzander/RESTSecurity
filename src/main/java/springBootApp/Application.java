package springBootApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import springBootApp.messaging.templates.Ticket;

@SpringBootApplication
@EnableJms
@ImportResource({ "classpath:app_config.xml",  "classpath:EmbeddedActiveMQ.xml"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        /*JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        jmsTemplate.convertAndSend("notificator", new Ticket(Topic.CLIENT,
                "Application has started"));*/
    }
}