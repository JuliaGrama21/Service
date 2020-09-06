package application.mapper;

import application.dto.CommunalOrganizationDto;
import application.model.CommunalOrganization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public CommunalOrganizationDto toDto(CommunalOrganization communalOrganization) {
        CommunalOrganizationDto communalOrganizationDto = new CommunalOrganizationDto();
        communalOrganizationDto.setId(communalOrganization.getId());
        communalOrganizationDto.setName(communalOrganization.getName());
        communalOrganizationDto.setType(communalOrganization.getOrganizationType());
        communalOrganizationDto.setTariff(communalOrganization.getTariff());
        return communalOrganizationDto;
    }

    public CommunalOrganization fromDto(CommunalOrganizationDto communalOrganizationDto) {
        CommunalOrganization communalOrganization = new CommunalOrganization();
        communalOrganization.setId(communalOrganizationDto.getId());
        return communalOrganization;
    }
}
