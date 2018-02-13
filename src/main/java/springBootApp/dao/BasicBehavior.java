package springBootApp.dao;

import springBootApp.entities.BasicEntity;

import java.util.List;

public interface BasicBehavior<T extends BasicEntity> {
    T getRecord(Long id);
    boolean removeRecord(Long id);
    boolean persist(T record);
    List<T> list();
}
