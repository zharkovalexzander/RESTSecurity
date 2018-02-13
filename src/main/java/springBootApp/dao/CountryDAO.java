package springBootApp.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import springBootApp.config.SpringConfigure;
import springBootApp.entities.CountryEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CountryDAO extends SpringConfigure implements CountryBehavior {

    @Override
    @Transactional
    public CountryEntity getRecord(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(CountryEntity.class, id);
    }

    @Override
    @Transactional
    public boolean removeRecord(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        CountryDAO p = session.load(CountryDAO.class, id);
        if(null != p){
            Transaction transaction = session.beginTransaction();
            try {
                transaction.begin();
                session.delete(p);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean persist(CountryEntity record) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            CountryEntity p = record.clone();
            session.persist(p);
            transaction.commit();
        } catch (CloneNotSupportedException e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<CountryEntity> list() {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM CountryEntity");
        return query.list();
    }

    @Override
    @Transactional
    public boolean checkCredentials(String name, String code) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM CountryEntity WHERE name = :name AND code = :code");
        query.setParameter("name", name);
        query.setParameter("code", code);
        return query.list().size() == 1;
    }
}
