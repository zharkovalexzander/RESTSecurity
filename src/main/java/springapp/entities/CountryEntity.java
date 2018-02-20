package springapp.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "country", schema = "world")
public class CountryEntity extends BasicEntity implements Serializable, Cloneable {
    private String code;
    private String name;
    private String continent;
    private String region;
    private Double surfaceArea;
    private Short indepYear;
    private Integer population;
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpOld;
    private String localName;
    private String governmentForm;
    private String headOfState;
    private Integer capital;
    private String code2;
    private String url;
    private Long id;
    private Integer visited;

    @Basic
    @Column(name = "visited")
    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Continent",
            columnDefinition="enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America')")
    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @Basic
    @Column(name = "Region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "SurfaceArea")
    public Double getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(Double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    @Basic
    @Column(name = "year")
    public Short getIndepYear() {
        return indepYear;
    }

    public void setIndepYear(Short indepYear) {
        this.indepYear = indepYear;
    }

    @Basic
    @Column(name = "Population")
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Basic
    @Column(name = "LifeExpectancy")
    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    @Basic
    @Column(name = "GNP")
    public Double getGnp() {
        return gnp;
    }

    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    @Basic
    @Column(name = "GNPOld")
    public Double getGnpOld() {
        return gnpOld;
    }

    public void setGnpOld(Double gnpOld) {
        this.gnpOld = gnpOld;
    }

    @Basic
    @Column(name = "LocalName")
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Basic
    @Column(name = "GovernmentForm")
    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    @Basic
    @Column(name = "HeadOfState")
    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    @Basic
    @Column(name = "Capital")
    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    @Basic
    @Column(name = "Code2")
    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    @Id
    @Column(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (continent != null ? !continent.equals(that.continent) : that.continent != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (surfaceArea != null ? !surfaceArea.equals(that.surfaceArea) : that.surfaceArea != null) return false;
        if (indepYear != null ? !indepYear.equals(that.indepYear) : that.indepYear != null) return false;
        if (population != null ? !population.equals(that.population) : that.population != null) return false;
        if (lifeExpectancy != null ? !lifeExpectancy.equals(that.lifeExpectancy) : that.lifeExpectancy != null)
            return false;
        if (gnp != null ? !gnp.equals(that.gnp) : that.gnp != null) return false;
        if (gnpOld != null ? !gnpOld.equals(that.gnpOld) : that.gnpOld != null) return false;
        if (localName != null ? !localName.equals(that.localName) : that.localName != null) return false;
        if (governmentForm != null ? !governmentForm.equals(that.governmentForm) : that.governmentForm != null)
            return false;
        if (headOfState != null ? !headOfState.equals(that.headOfState) : that.headOfState != null) return false;
        if (capital != null ? !capital.equals(that.capital) : that.capital != null) return false;
        if (code2 != null ? !code2.equals(that.code2) : that.code2 != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (continent != null ? continent.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (surfaceArea != null ? surfaceArea.hashCode() : 0);
        result = 31 * result + (indepYear != null ? indepYear.hashCode() : 0);
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (lifeExpectancy != null ? lifeExpectancy.hashCode() : 0);
        result = 31 * result + (gnp != null ? gnp.hashCode() : 0);
        result = 31 * result + (gnpOld != null ? gnpOld.hashCode() : 0);
        result = 31 * result + (localName != null ? localName.hashCode() : 0);
        result = 31 * result + (governmentForm != null ? governmentForm.hashCode() : 0);
        result = 31 * result + (headOfState != null ? headOfState.hashCode() : 0);
        result = 31 * result + (capital != null ? capital.hashCode() : 0);
        result = 31 * result + (code2 != null ? code2.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String entityName() {
        return "CountryEntity";
    }

    @Override
    public CountryEntity clone() throws CloneNotSupportedException {
        return (CountryEntity) super.clone();
    }
}
