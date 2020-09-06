package application.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class CommunalOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    private OrganizationType organizationType;

    @OneToMany(mappedBy = "communalOrganization", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Range(min = 0)
    private BigDecimal tariff;

    @OneToMany(mappedBy = "communalOrganization")
    private List<Balance> userBalances;



}
