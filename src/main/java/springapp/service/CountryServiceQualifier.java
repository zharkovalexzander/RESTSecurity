package springapp.service;

import springapp.entities.CountryEntity;

public interface CountryServiceQualifier extends BasicService<CountryEntity> {
    String checkCredentials(String name, String code);
    String removeSession(String token);
    String checkSession(String token);
    String visitCountry(String code, String token);
}
