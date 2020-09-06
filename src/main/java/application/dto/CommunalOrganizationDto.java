package application.dto;

import application.model.OrganizationType;

import java.math.BigDecimal;

public class CommunalOrganizationDto {
    private Long id;
    private String name;
    private BigDecimal tariff;
    private OrganizationType type;

    public CommunalOrganizationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }
}
