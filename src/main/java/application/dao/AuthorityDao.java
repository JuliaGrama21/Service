package application.dao;

import application.model.security.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityDao extends CrudRepository<Authority, Authority.AuthorityType> {
}
