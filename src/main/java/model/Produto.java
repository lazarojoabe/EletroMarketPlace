package model;

import java.sql.Timestamp;

public class Produto {
    private Long idProduto;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private Long idCategoria;
    private Long idVendedor;
    private Timestamp dataCadastro;
    private boolean ativo; // Nova flag para Soft Delete

   
    public Produto() {}

    public Produto(String nome, String descricao, double preco, int estoque, Long idCategoria, Long idVendedor) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.idCategoria = idCategoria;
        this.idVendedor = idVendedor;
    }

    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public Long getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Long idVendedor) { this.idVendedor = idVendedor; }

    public Timestamp getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Timestamp dataCadastro) { this.dataCadastro = dataCadastro; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return "Produto{id=" + idProduto + ", nome='" + nome + "', preco=" + preco + "}";
    }
}
