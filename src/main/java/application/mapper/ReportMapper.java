package application.mapper;

import application.dto.ReportDto;
import application.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportDto toDto(Payment payment){
        ReportDto reportDto = new ReportDto();
        reportDto.setOrganizationType(payment.getCommunalOrganization().getOrganizationType().name());
        reportDto.setAmount(payment.getAmount());
        reportDto.setTime(payment.getTime());
        return reportDto;
    }
}
