package model;

import java.sql.Timestamp;

public class Pedido {
    private Long idPedido;
    private Long idProduto;
    private Long idVendedor;
    private int quantidade;
    private Timestamp dataPedido;

    public Pedido() {}

    public Pedido(Long idProduto, Long idVendedor, int quantidade) {
        this.idProduto = idProduto;
        this.idVendedor = idVendedor;
        this.quantidade = quantidade;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public Long getIdProduto() { return idProduto; }
    public void setIdProduto(Long idProduto) { this.idProduto = idProduto; }

    public Long getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Long idVendedor) { this.idVendedor = idVendedor; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public Timestamp getDataPedido() { return dataPedido; }
    public void setDataPedido(Timestamp dataPedido) { this.dataPedido = dataPedido; }

    @Override
    public String toString() {
        return "Pedido{id=" + idPedido + ", produto=" + idProduto + ", qtd=" + quantidade + "}";
    }
}
