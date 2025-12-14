package model;

public class Vendedor {
    private Long idVendedor;
    private String nome;
    private String email;

    public Vendedor() {}

    public Vendedor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getIdVendedor() {
        return idVendedor;
    }
    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Vendedor{id=" + idVendedor + ", nome='" + nome + "', email='" + email + "'}";
    }
}
