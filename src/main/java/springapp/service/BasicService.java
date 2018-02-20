package springapp.service;

import org.springframework.stereotype.Service;
import springapp.entities.BasicEntity;

import java.util.List;

@Service
public interface BasicService<T extends BasicEntity> {
    T getRecord(Long id, String token);
    boolean removeRecord(Long id, String token);
    void persist(T record, String token);
    List<T> list(String token);
}
