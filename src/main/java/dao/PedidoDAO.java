package dao;

import db.ConnectionFactory;
import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean inserir(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_produto, id_vendedor, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, pedido.getIdProduto());
            stmt.setLong(2, pedido.getIdVendedor());
            stmt.setInt(3, pedido.getQuantidade());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) pedido.setIdPedido(rs.getLong(1));
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pedido: " + e.getMessage());
        }
        return false;
    }

    public Pedido buscarPorId(Long id) {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        Pedido p = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = extrairPedido(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }
        return p;
    }

    public boolean atualizar(Pedido pedido) {
        String sql = " UPDATE pedidos SET id_produto = ?, id_vendedor = ?, quantidade = ? WHERE id_pedido = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pedido.getIdProduto());
            stmt.setLong(2, pedido.getIdVendedor());
            stmt.setInt(3, pedido.getQuantidade());
            stmt.setLong(4, pedido.getIdPedido());

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

    public List<Pedido> listarTodos() {
        String sql = "SELECT p.*, prod.nome AS nome_produto, vend.nome AS nome_vendedor " +
                     "FROM pedidos p " +
                     "LEFT JOIN produtos prod ON p.id_produto = prod.id_produto " +
                     "LEFT JOIN vendedores vend ON p.id_vendedor = vend.id_vendedor " +
                     "ORDER BY p.data_pedido DESC";
        
        List<Pedido> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getLong("id_pedido"));
                p.setIdProduto(rs.getLong("id_produto"));
                p.setIdVendedor(rs.getLong("id_vendedor"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setDataPedido(rs.getTimestamp("data_pedido"));
                
                // Atribui os nomes vindos do JOIN
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setNomeVendedor(rs.getString("nome_vendedor"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos completos: " + e.getMessage());
        }
        return lista;
    }
    public List<Pedido> listarPorVendedor(Long idVendedor) {
        String sql = "SELECT * FROM pedidos WHERE id_vendedor = ?";
        List<Pedido> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idVendedor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) lista.add(extrairPedido(rs));

        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos por vendedor: " + e.getMessage());
        }
        return lista;
    }

    public List<Pedido> listarPorProduto(Long idProduto) {
        String sql = "SELECT * FROM pedidos WHERE id_produto = ?";
        List<Pedido> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idProduto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) lista.add(extrairPedido(rs));

        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos por produto: " + e.getMessage());
        }
        return lista;
    }

    private Pedido extrairPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setIdPedido(rs.getLong("id_pedido"));
        p.setIdProduto(rs.getLong("id_produto"));
        p.setIdVendedor(rs.getLong("id_vendedor"));
        p.setQuantidade(rs.getInt("quantidade"));
        p.setDataPedido(rs.getTimestamp("data_pedido"));
        return p;
    }
}
