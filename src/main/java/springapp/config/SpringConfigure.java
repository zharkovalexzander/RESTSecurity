package springapp.config;

import org.hibernate.SessionFactory;

public abstract class SpringConfigure {
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
