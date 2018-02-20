package springapp.dao;

import springapp.entities.CountryEntity;

public interface CountryBehavior extends BasicBehavior<CountryEntity> {
    boolean checkCredentials(String name, String code);
    Integer visitCountry(String code);
}
