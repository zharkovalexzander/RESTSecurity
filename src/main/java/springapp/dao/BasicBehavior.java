package springapp.dao;

import springapp.entities.BasicEntity;

import java.util.List;

public interface BasicBehavior<T extends BasicEntity> {
    T getRecord(Long id);
    boolean removeRecord(Long id);
    void persist(T record);
    List<T> list();
}
