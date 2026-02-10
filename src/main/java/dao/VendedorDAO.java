package dao;

import db.ConnectionFactory;
import model.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {

    // Adicionado o campo 'ativo' na inserção
    public boolean inserir(Vendedor vendedor) {
        String sql = "INSERT INTO vendedores (nome, email, ativo) VALUES (?, ?, true)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) vendedor.setIdVendedor(rs.getLong(1));
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir vendedor: " + e.getMessage());
        }
        return false;
    }

    // Busca apenas se o vendedor estiver ativo
    public Vendedor buscarPorId(Long id) {
        String sql = "SELECT * FROM vendedores WHERE id_vendedor = ? AND ativo = true";
        Vendedor v = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                v = new Vendedor();
                v.setIdVendedor(rs.getLong("id_vendedor"));
                v.setNome(rs.getString("nome"));
                v.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendedor: " + e.getMessage());
        }
        return v;
    }

    // Busca apenas se o vendedor estiver ativo
    public Vendedor buscarPorEmail(String email) {
        String sql = "SELECT * FROM vendedores WHERE email = ? AND ativo = true";
        Vendedor v = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                v = new Vendedor();
                v.setIdVendedor(rs.getLong("id_vendedor"));
                v.setNome(rs.getString("nome"));
                v.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendedor por email: " + e.getMessage());
        }
        return v;
    }

    public boolean atualizar(Vendedor vendedor) {
        String sql = "UPDATE vendedores SET nome = ?, email = ? WHERE id_vendedor = ? AND ativo = true";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendedor.getNome());
            stmt.setString(2, vendedor.getEmail());
            stmt.setLong(3, vendedor.getIdVendedor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar vendedor: " + e.getMessage());
        }
        return false;
    }

    /**
     * MUDANÇA PARA SOFT DELETE
     * Atualiza a flag 'ativo' para falso em vez de deletar o registro
     */
    public boolean remover(Long id) {
        String sql = "UPDATE vendedores SET ativo = false WHERE id_vendedor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover vendedor: " + e.getMessage());
        }
        return false;
    }

    // Lista apenas vendedores que não foram "excluídos" logicamente
    public List<Vendedor> listarTodos() {
        String sql = "SELECT * FROM vendedores WHERE ativo = true ORDER BY nome";
        List<Vendedor> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vendedor v = new Vendedor();
                v.setIdVendedor(rs.getLong("id_vendedor"));
                v.setNome(rs.getString("nome"));
                v.setEmail(rs.getString("email"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendedores: " + e.getMessage());
        }
        return lista;
    }
}