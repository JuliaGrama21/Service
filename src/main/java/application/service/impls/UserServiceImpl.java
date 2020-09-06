package application.service.impls;

import application.dao.UserDao;
import application.model.security.Authority;
import application.model.security.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isExist(User user) {
        return userDao.existsByUsername(user.getUsername());
    }

    @Override
    public void create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthorities(Collections.singletonList(Authority.USER));
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findUserById(Long userId) {
            return userDao.findById(userId).get();

    }

    @Override
    public List<String> findAllUsernames() {
        return userDao.findAllUsernames();
    }

}