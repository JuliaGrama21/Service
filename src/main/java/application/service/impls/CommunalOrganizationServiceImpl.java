package application.service.impls;

import application.dao.CommunalOrganizationDao;
import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.service.CommunalOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommunalOrganizationServiceImpl implements CommunalOrganizationService {

    @Autowired
    private CommunalOrganizationDao communalOrganizationDao;

    public List<CommunalOrganization> findByOrganizationType(OrganizationType organizationType) {
        return communalOrganizationDao.findByOrganizationType(organizationType);
    }

    @Override
    public List<CommunalOrganization> findAllOrg() {
        return (List<CommunalOrganization>) communalOrganizationDao.findAll();
    }

    public <S extends CommunalOrganization> S save(S s) {
        return communalOrganizationDao.save(s);
    }

    public <S extends CommunalOrganization> Iterable<S> saveAll(Iterable<S> iterable) {
        return communalOrganizationDao.saveAll(iterable);
    }

    public CommunalOrganization findById(Long aLong) {
        return communalOrganizationDao.findById(aLong).get();
    }

    public boolean existsById(Long aLong) {
        return communalOrganizationDao.existsById(aLong);
    }

    public Iterable<CommunalOrganization> findAll() {
        return communalOrganizationDao.findAll();
    }

    public Iterable<CommunalOrganization> findAllById(Iterable<Long> iterable) {
        return communalOrganizationDao.findAllById(iterable);
    }

    public long count() {
        return communalOrganizationDao.count();
    }

    public void deleteById(Long aLong) {
        communalOrganizationDao.deleteById(aLong);
    }

    public void delete(CommunalOrganization communalOrganization) {
        communalOrganizationDao.delete(communalOrganization);
    }

    public void deleteAll(Iterable<? extends CommunalOrganization> iterable) {
        communalOrganizationDao.deleteAll(iterable);
    }

    public void deleteAll() {
        communalOrganizationDao.deleteAll();
    }
}
