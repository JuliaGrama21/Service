package application.service.impls;

import application.dao.PaymentDao;
import application.model.Account;
import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.model.Payment;
import application.model.security.User;
import application.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public List<Payment> findAllPaymentsByUserId(Long userId) {
        return paymentDao.findAllPaymentsByUserId(userId);
    }

    @Override
    public void save(Payment payment) {
        paymentDao.save(payment);
    }

    @Override
    public void delete(Payment payment) {
        paymentDao.delete(payment);
    }

    @Override
    public Payment findPaymentById(Long paymentId) {
        return paymentDao.findById(paymentId).get();
    }

    @Override
    public List<Payment> findAll() {
        return (List<Payment>) paymentDao.findAll();
    }

    @Override
    public List<Payment> findByOrganizationAndAccount(CommunalOrganization communalOrganization, Account account) {
        return paymentDao.findAllByCommunalOrganizationAndAccount(communalOrganization, account);
    }

    @Override
    public Payment findLastByOrganizationIdAndAccount(Long organizationId, Account account) {
        return paymentDao.findFirstByCommunalOrganizationIdAndAccountOrderByIdDesc(organizationId, account);
    }
}