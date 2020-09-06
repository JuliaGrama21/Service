package application.service;

import application.model.Account;
import application.model.security.User;

public interface AccountService {
    void save(Account account);
    Account findById(Long id);
    Account findByUser(User user);
}
