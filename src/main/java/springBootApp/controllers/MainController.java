package springBootApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springBootApp.entities.CountryEntity;
import springBootApp.service.CountryService;

import java.util.List;

@Controller
@Configurable
public class MainController {
    private CountryService countryService;

    @Autowired
    @Qualifier(value = "countryService")
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/countries/{id}", method = RequestMethod.POST)
    public @ResponseBody
    CountryEntity getCountry(@PathVariable("id") Long id,
                             @RequestParam("t") String token) {
        return this.countryService.getRecord(id, token);
    }

    @RequestMapping(value = "/countries", method = RequestMethod.POST)
    public @ResponseBody
    List<CountryEntity> getCountries(@RequestParam("t") String token) {
        return this.countryService.list(token);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public @ResponseBody
    String auth(@RequestParam("name") String name,
                @RequestParam("code") String code) {
        return this.countryService.checkCredentials(name, code);
    }

    @RequestMapping(value = "/rm", method = RequestMethod.GET)
    public @ResponseBody
    String remove(@RequestParam("t") String token) {
        return this.countryService.removeSession(token);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public @ResponseBody
    String check(@RequestParam("t") String token) {
        return this.countryService.checkSession(token);
    }

}
