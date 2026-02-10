package dao;

import db.ConnectionFactory;
import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Na listagem, buscamos os nomes mesmo que o item não esteja mais "ativo" para novas vendas
    public List<Pedido> listarTodos() {
        String sql = "SELECT p.*, pr.nome AS nome_produto, v.nome AS nome_vendedor " +
                     "FROM pedidos p " +
                     "LEFT JOIN produtos pr ON p.id_produto = pr.id_produto " +
                     "LEFT JOIN vendedores v ON p.id_vendedor = v.id_vendedor " +
                     "ORDER BY p.data_pedido DESC";
        
        List<Pedido> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setIdPedido(rs.getLong("id_pedido"));
                ped.setIdProduto(rs.getLong("id_produto"));
                ped.setIdVendedor(rs.getLong("id_vendedor"));
                ped.setQuantidade(rs.getInt("quantidade"));
                ped.setDataPedido(rs.getTimestamp("data_pedido"));
                
                // Atribui os nomes recuperados pelo JOIN para o histórico
                ped.setNomeProduto(rs.getString("nome_produto"));
                ped.setNomeVendedor(rs.getString("nome_vendedor"));
                
                lista.add(ped);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    public Pedido buscarPorId(Long id) {
        String sql = "SELECT p.*, pr.nome AS nome_produto, v.nome AS nome_vendedor " +
                     "FROM pedidos p " +
                     "LEFT JOIN produtos pr ON p.id_produto = pr.id_produto " +
                     "LEFT JOIN vendedores v ON p.id_vendedor = v.id_vendedor " +
                     "WHERE p.id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pedido ped = new Pedido();
                ped.setIdPedido(rs.getLong("id_pedido"));
                ped.setQuantidade(rs.getInt("quantidade"));
                ped.setDataPedido(rs.getTimestamp("data_pedido"));
                ped.setNomeProduto(rs.getString("nome_produto"));
                ped.setNomeVendedor(rs.getString("nome_vendedor"));
                return ped;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }
        return null;
    }

    public boolean inserir(Pedido p) {
        String sql = "INSERT INTO pedidos (id_produto, id_vendedor, quantidade) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, p.getIdProduto());
            stmt.setLong(2, p.getIdVendedor());
            stmt.setInt(3, p.getQuantidade());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) p.setIdPedido(rs.getLong(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pedido: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizar(Pedido p) {
        String sql = "UPDATE pedidos SET quantidade = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getQuantidade());
            stmt.setLong(2, p.getIdPedido());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
        }
        return false;
    }

    public boolean remover(Long id) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover pedido: " + e.getMessage());
        }
        return false;
    }
}