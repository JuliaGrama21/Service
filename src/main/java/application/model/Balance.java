package application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Balance {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private CommunalOrganization communalOrganization;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
