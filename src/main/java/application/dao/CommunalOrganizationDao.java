package application.dao;

import application.model.CommunalOrganization;
import application.model.OrganizationType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommunalOrganizationDao extends CrudRepository<CommunalOrganization, Long> {
    List<CommunalOrganization> findByOrganizationType(OrganizationType organizationType);
}
