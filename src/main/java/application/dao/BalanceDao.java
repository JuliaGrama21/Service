package application.dao;

import application.model.Account;
import application.model.Balance;
import application.model.CommunalOrganization;
import org.springframework.data.repository.CrudRepository;

public interface BalanceDao extends CrudRepository<Balance, Long> {
    Balance findFirstByAccountIdAndCommunalOrganizationId(Long accountId, Long communalOrganizationId);
    Balance findFirstByAccountAndCommunalOrganization(Account account, CommunalOrganization communalOrganization);
}
