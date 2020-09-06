package application.mapper;

import application.dto.PaymentDto;
import application.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto toDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setIndicator(payment.getIndicator());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaid(payment.getIsPaid());
        paymentDto.setTime(payment.getTime());
        paymentDto.setUserName(payment.getAccount().getUser().getUsername());
        paymentDto.setOrganizationName(payment.getCommunalOrganization().getName());
        paymentDto.setType(payment.getCommunalOrganization().getOrganizationType().toString());
        return paymentDto;
    }

    public Payment fromDto(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setIndicator(paymentDto.getIndicator());
        payment.setTime(paymentDto.getTime());
        return payment;
    }
}
