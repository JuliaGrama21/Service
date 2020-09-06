package application.dao;

import application.model.Account;
import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.model.Payment;
import application.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PaymentDao extends CrudRepository<Payment, Long> {


    @Query(value = "SELECT * FROM payment WHERE user_id = :userId", nativeQuery = true)
    List<Payment> findAllPaymentsByUserId(@Param("userId")Long userId);

    List<Payment> findAllByCommunalOrganizationAndAccount(CommunalOrganization communalOrganization, Account account);

    Payment findFirstByCommunalOrganizationIdAndAccountOrderByIdDesc(Long organizationId, Account account);

}
