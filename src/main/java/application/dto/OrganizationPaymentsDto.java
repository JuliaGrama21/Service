package application.dto;

import application.model.Payment;

import java.util.List;

public class OrganizationPaymentsDto {

    private String organizationType;
    private List<PaymentDto> payments;

    public OrganizationPaymentsDto() {
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public List<PaymentDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDto> payments) {
        this.payments = payments;
    }
}
