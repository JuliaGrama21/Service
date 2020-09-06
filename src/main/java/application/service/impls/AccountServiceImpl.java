package application.service.impls;

import application.dao.AccountDao;
import application.model.Account;
import application.model.security.User;
import application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountDao.findById(id).orElse(null);
    }

    @Override
    public Account findByUser(User user) {
        return accountDao.findByUser(user);
    }

}
