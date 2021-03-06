package curso.springboot.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Pessoa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode estar vazio")
    private String nome;

    @NotNull(message = "sobrenome não pode ser nulo")
    @NotEmpty(message = "Sobrenome não pode estar vazio")
    private String sobrenome;

    @Min(value = 17, message = "Novin demais")
    private int idade;

    private String cep;
    private String cidade;
    private String rua;
    private String bairro;
    private String uf;
    private String ibge;

    private String sexopessoa;

    @ManyToOne
    private Profissao profissao;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Lob
    private byte[] curriculo;

    private String nomeFileCurriculo;
    private String tipoFileCurriculo;

    public String getNomeFileCurriculo() {
        return nomeFileCurriculo;
    }

    public void setNomeFileCurriculo(String nomeFileCurriculo) {
        this.nomeFileCurriculo = nomeFileCurriculo;
    }

    public String getTipoFileCurriculo() {
        return tipoFileCurriculo;
    }

    public void setTipoFileCurriculo(String tipoFileCurriculo) {
        this.tipoFileCurriculo = tipoFileCurriculo;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
        this.curriculo = curriculo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public String getSexopessoa() {
        return sexopessoa;
    }

    public void setSexopessoa(String sexopessoa) {
        this.sexopessoa = sexopessoa;
    }


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Dados> telefones;

    public List<Dados> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Dados> telefones) {
        this.telefones = telefones;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
