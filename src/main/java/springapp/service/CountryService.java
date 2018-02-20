package springapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springapp.dao.CountryDAO;
import springapp.entities.CountryEntity;
import springapp.messaging.senders.JmsMessageSender;
import springapp.messaging.templates.Ticket;
import springapp.respond.Code;
import springapp.security.SessionCredentials;
import springapp.security.SessionSecurity;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService implements CountryServiceQualifier {
    private CountryDAO countryDAO;
    private SessionSecurity sessionSecurity;
    private JmsMessageSender jmsMessageSender;

    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    public void setSessionSecurity(SessionSecurity sessionSecurity) {
        this.sessionSecurity = sessionSecurity;
    }

    public void setJmsMessageSender(JmsMessageSender jmsMessageSender) {
        this.jmsMessageSender = jmsMessageSender;
    }

    @Override
    @Transactional
    public CountryEntity getRecord(Long id, String token) {
        CountryEntity answer = null;
        if (sessionSecurity.sessionExists(token)) {
            this.jmsMessageSender.sendFormattedTicket(new Ticket("SERVER",
                    "Access granted. Getting the record..."));
            answer = countryDAO.getRecord(id);
        }
        return answer;
    }

    @Override
    @Transactional
    public boolean removeRecord(Long id, String token) {
        return countryDAO.removeRecord(id);
    }

    @Override
    @Transactional
    public void persist(CountryEntity record, String token) {
        countryDAO.persist(record);
    }

    @Override
    @Transactional
    public List<CountryEntity> list(String token) {
        List<CountryEntity> answer = new ArrayList<>();
        if (sessionSecurity.sessionExists(token)) {
            this.jmsMessageSender.sendFormattedTicket(new Ticket("SERVER",
                    "Access granted. Listing the data..."));
            answer = this.countryDAO.list();
        }
        return answer;
    }

    @Override
    @Transactional
    public String checkCredentials(String name, String code) {
        boolean foundCredentials = this.countryDAO.checkCredentials(name, code);
        String session = "30"; //invalid data
        if (foundCredentials) {
            this.jmsMessageSender.sendFormattedTicket(new Ticket("SERVER",
                    "Requested credentials were found in the database. Processing..."));
            SessionCredentials sessionCredentials = new SessionCredentials();
            sessionCredentials.newCredential("name", name);
            sessionCredentials.newCredential("code", code);
            boolean foundSessionCredentials = sessionSecurity.credentialsArePresent(sessionCredentials);
            if (!foundSessionCredentials) {
                this.jmsMessageSender.sendFormattedTicket(new Ticket("SERVER",
                        "There is no session with such credentials. Setting up new session..."));
                session = sessionSecurity.newSession(sessionCredentials);
            } else {
                this.jmsMessageSender.sendFormattedTicket(new Ticket("SERVER",
                        "The user is authorized."));
                session = Code.AUTHORIZED.getEquivalence(); //authorized
            }
        }
        return session;
    }

    @Override
    public String removeSession(String token) {
        if (sessionSecurity.removeSession(token)) {
            return Code.SUCCESS.getEquivalence(); //21
        }
        return Code.INVALID_INPUT_DATA.getEquivalence(); //30
    }

    @Override
    public String checkSession(String token) {
        boolean existence = this.sessionSecurity.sessionExists(token);
        if(existence) {
            return Code.AUTHORIZED.getEquivalence();
        }
        return Code.NO_CONTENT_AVAILABLE.getEquivalence();
    }

    @Override
    public String visitCountry(String code, String token) {
        boolean existence = this.sessionSecurity.sessionExists(token);
        if(existence) {
            return this.countryDAO.visitCountry(code).toString();
        }
        return Code.RESTRICTED.getEquivalence();
    }

}
