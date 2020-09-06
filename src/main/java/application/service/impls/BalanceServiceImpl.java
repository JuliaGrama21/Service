package application.service.impls;

import application.dao.BalanceDao;
import application.model.Account;
import application.model.Balance;
import application.model.CommunalOrganization;
import application.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceDao balanceDao;

    public Balance findByAccountIdAndCommunalOrganizationId(Long accountId, Long communalOrganizationId) {
        return balanceDao.findFirstByAccountIdAndCommunalOrganizationId(accountId, communalOrganizationId);
    }

    public Balance findByAccountAndCommunalOrganization(Account account, CommunalOrganization communalOrganization) {
        return balanceDao.findFirstByAccountAndCommunalOrganization(account, communalOrganization);
    }

    public <S extends Balance> S save(S s) {
        return balanceDao.save(s);
    }

    public <S extends Balance> Iterable<S> saveAll(Iterable<S> iterable) {
        return balanceDao.saveAll(iterable);
    }

    public Optional<Balance> findById(Long aLong) {
        return balanceDao.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return balanceDao.existsById(aLong);
    }

    public Iterable<Balance> findAll() {
        return balanceDao.findAll();
    }

    public Iterable<Balance> findAllById(Iterable<Long> iterable) {
        return balanceDao.findAllById(iterable);
    }

    public long count() {
        return balanceDao.count();
    }

    public void deleteById(Long aLong) {
        balanceDao.deleteById(aLong);
    }

    public void delete(Balance balance) {
        balanceDao.delete(balance);
    }

    public void deleteAll(Iterable<? extends Balance> iterable) {
        balanceDao.deleteAll(iterable);
    }

    public void deleteAll() {
        balanceDao.deleteAll();
    }
}
