package application.service.impls;

import application.dao.AuthorityDao;
import application.model.security.Authority;
import application.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    public <S extends Authority> S save(S s) {
        return authorityDao.save(s);
    }

    public <S extends Authority> Iterable<S> saveAll(Iterable<S> iterable) {
        return authorityDao.saveAll(iterable);
    }

    public Optional<Authority> findById(Authority.AuthorityType authorityType) {
        return authorityDao.findById(authorityType);
    }

    public boolean existsById(Authority.AuthorityType authorityType) {
        return authorityDao.existsById(authorityType);
    }

    public Iterable<Authority> findAll() {
        return authorityDao.findAll();
    }

    public Iterable<Authority> findAllById(Iterable<Authority.AuthorityType> iterable) {
        return authorityDao.findAllById(iterable);
    }

    public long count() {
        return authorityDao.count();
    }

    public void deleteById(Authority.AuthorityType authorityType) {
        authorityDao.deleteById(authorityType);
    }

    public void delete(Authority authority) {
        authorityDao.delete(authority);
    }

    public void deleteAll(Iterable<? extends Authority> iterable) {
        authorityDao.deleteAll(iterable);
    }

    public void deleteAll() {
        authorityDao.deleteAll();
    }
}
