package application.model.security;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    public static final Authority USER = new Authority(AuthorityType.USER);
    public static final Authority ORGANIZATION_OWNER = new Authority(AuthorityType.ORGANIZATION_OWNER);
    public static final Authority ADMIN = new Authority(AuthorityType.ADMIN);

    public enum AuthorityType {
        USER, ORGANIZATION_OWNER, ADMIN
    }

    @Id
    @Column(name = "name", nullable = false, length = 36)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    public Authority() {}

    public Authority(AuthorityType authority) {
        this.authority = authority;
    }

    public Authority(String authority) {
        this.authority = AuthorityType.valueOf(authority);
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }
    public void setAuthority(AuthorityType authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority1 = (Authority) o;

        return authority == authority1.authority;
    }

    @Override
    public int hashCode() {
        return authority != null ? authority.hashCode() : 0;
    }

}