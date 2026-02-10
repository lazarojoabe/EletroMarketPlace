package dao;

import db.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Adicionado o campo 'ativo' na inserção
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, descricao, preco, estoque, id_categoria, id_vendedor, ativo) VALUES (?, ?, ?, ?, ?, ?, true)";

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

    // Busca apenas se o produto estiver ativo
    public Produto buscarPorId(Long id) {
        String sql = "SELECT * FROM produtos WHERE id_produto = ? AND ativo = true";
        Produto p = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Produto();
                p.setIdProduto(rs.getLong("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                p.setIdCategoria(rs.getLong("id_categoria"));
                p.setIdVendedor(rs.getLong("id_vendedor"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
        return p;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, id_categoria = ?, id_vendedor = ? WHERE id_produto = ? AND ativo = true";

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

    /**
     * MUDANÇA PARA SOFT DELETE
     * Desativa o produto para que os pedidos antigos não percam a referência
     */
    public boolean remover(Long id) {
        String sql = "UPDATE produtos SET ativo = false WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto: " + e.getMessage());
        }
        return false;
    }

    // Lista apenas produtos ativos no estoque
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos WHERE ativo = true ORDER BY nome";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getLong("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }
}