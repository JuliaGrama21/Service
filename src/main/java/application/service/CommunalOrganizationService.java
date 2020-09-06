package application.service;

import application.model.CommunalOrganization;
import application.model.OrganizationType;

import java.util.List;

public interface CommunalOrganizationService {

    List<CommunalOrganization> findByOrganizationType(OrganizationType organizationType);
    CommunalOrganization findById(Long aLong);
    List<CommunalOrganization> findAllOrg();

}
