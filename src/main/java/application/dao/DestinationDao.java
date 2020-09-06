package application.dao;

import application.model.CommunalOrganization;
import org.springframework.data.repository.CrudRepository;

public interface DestinationDao extends CrudRepository<CommunalOrganization, Long> {
}
