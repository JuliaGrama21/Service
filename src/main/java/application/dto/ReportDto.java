package application.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReportDto {
    private String organizationType;
    private BigDecimal amount;
    private Date time;

    public ReportDto(){}
    public ReportDto(String organizationType, BigDecimal amount, Date time) {
        this.organizationType = organizationType;
        this.amount = amount;
        this.time = time;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
