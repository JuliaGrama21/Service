package application.service;

import application.model.Account;
import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.model.Payment;
import application.model.security.User;

import java.util.Date;
import java.util.List;

public interface PaymentService {
    List<Payment> findAllPaymentsByUserId(Long userId);
    void save(Payment payment);
    void delete(Payment payment);
    Payment findPaymentById(Long paymentId);
    List<Payment> findAll();
    List<Payment> findByOrganizationAndAccount(CommunalOrganization communalOrganization, Account account);
    Payment findLastByOrganizationIdAndAccount(Long organizationId, Account account);
}
