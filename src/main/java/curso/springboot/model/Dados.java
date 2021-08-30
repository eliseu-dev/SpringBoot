package curso.springboot.model;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
public class Dados {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String telefone;
    private String email;

    @ManyToOne
    @org.hibernate.annotations.ForeignKey(name = "pessoa_id")
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


}
