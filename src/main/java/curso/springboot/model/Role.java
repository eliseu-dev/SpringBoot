package curso.springboot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nomeRole;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.nomeRole;
    }

    public String getNomeRole() {
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }

}
