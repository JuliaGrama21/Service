package application.configuration.security;

import application.model.CommunalOrganization;
import application.model.OrganizationType;
import application.model.security.Authority;
import application.service.impls.AuthorityServiceImpl;
import application.service.impls.CommunalOrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
// This component will init authorities using Authority.AuthorityType
public class DbInitializer {

    @Autowired
    private AuthorityServiceImpl authorityService;

    @Autowired
    private CommunalOrganizationServiceImpl communalOrganizationService;

    @PostConstruct
    private void initAuthority() {
        Arrays.stream(Authority.AuthorityType.values()).forEach(authorityType -> {
            Authority authority = authorityService.findById(authorityType).orElse(null);

            if (authority == null) {
                authorityService.save(new Authority(authorityType));
            }
        });
    }

    @PostConstruct
    private void initCommunalOrganization() {
        Arrays.stream(OrganizationType.values()).forEach(organizationType -> {
            List<CommunalOrganization> organizations = communalOrganizationService.findByOrganizationType(organizationType);

            if (organizations != null && !organizations.isEmpty()) {
                return; // Something exists
            }

            CommunalOrganization communalOrganization = new CommunalOrganization();
            communalOrganization.setOrganizationType(organizationType);

            if (organizationType.equals(OrganizationType.GAS)) {
                communalOrganization.setName("Gas Organization");
            }

            if (organizationType.equals(OrganizationType.ELECTRICITY)) {
                communalOrganization.setName("Electricity Organization");
            }

            if (organizationType.equals(OrganizationType.WATER)) {
                communalOrganization.setName("Water Organization");
            }

            if (organizationType.equals(OrganizationType.INTERNET)) {
                communalOrganization.setName("Internet Organization");
            }

            if (organizationType.equals(OrganizationType.HOME_PHONE)) {
                communalOrganization.setName("Phone Organization");
            }

            communalOrganizationService.save(communalOrganization);
        });

    }

}
