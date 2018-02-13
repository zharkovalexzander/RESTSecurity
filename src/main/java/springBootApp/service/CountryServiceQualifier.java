package springBootApp.service;

import springBootApp.entities.CountryEntity;

public interface CountryServiceQualifier extends BasicService<CountryEntity> {
    String checkCredentials(String name, String code);
    String removeSession(String token);
}
