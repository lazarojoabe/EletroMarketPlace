package dao;

import db.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, id_categoria, id_vendedor) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setLong(5, produto.getIdCategoria());
            stmt.setLong(6, produto.getIdVendedor());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) produto.setIdProduto(rs.getLong(1));
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
        }
        return false;
    }

    public Produto buscarPorId(Long id) {
        String sql = "SELECT * FROM produtos WHERE id_produto = ?";
        Produto p = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = extrairProduto(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
        return p;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, id_categoria = ?, id_vendedor = ? WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setLong(5, produto.getIdCategoria());
            stmt.setLong(6, produto.getIdVendedor());
            stmt.setLong(7, produto.getIdProduto());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizarEstoque(Long id, int novoEstoque) {
        String sql = "UPDATE produtos SET estoque = ? WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novoEstoque);
            stmt.setLong(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
        }
        return false;
    }

    public boolean remover(Long id) {
        String sql = "DELETE FROM produtos WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto: " + e.getMessage());
        }
        return false;
    }

    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(extrairProduto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> listarPorCategoria(Long idCategoria) {
        String sql = "SELECT * FROM produtos WHERE id_categoria = ?";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idCategoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(extrairProduto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos por categoria: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> listarPorVendedor(Long idVendedor) {
        String sql = "SELECT * FROM produtos WHERE id_vendedor = ?";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idVendedor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(extrairProduto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos por vendedor: " + e.getMessage());
        }
        return lista;
    }

    private Produto extrairProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setIdProduto(rs.getLong("id_produto"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setPreco(rs.getDouble("preco"));
        p.setEstoque(rs.getInt("estoque"));
        p.setIdCategoria(rs.getLong("id_categoria"));
        p.setIdVendedor(rs.getLong("id_vendedor"));
        p.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return p;
    }
}
