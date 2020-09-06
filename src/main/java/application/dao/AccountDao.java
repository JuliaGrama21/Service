package application.dao;

import application.model.Account;
import application.model.security.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountDao extends CrudRepository<Account, Long> {

    Account findByUser(User user);

}
