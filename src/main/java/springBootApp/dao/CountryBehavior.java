package springBootApp.dao;

import springBootApp.entities.CountryEntity;

public interface CountryBehavior extends BasicBehavior<CountryEntity> {
    boolean checkCredentials(String name, String code);
}
