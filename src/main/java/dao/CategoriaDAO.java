package dao;

import db.ConnectionFactory;
import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

 
    public boolean inserir(Categoria categoria) {
        String sql = "INSERT INTO categorias (nome, ativo) VALUES (?, true)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNome());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) categoria.setIdCategoria(rs.getLong(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Busca apenas se a categoria estiver ativa
    public Categoria buscarPorId(Long id) {
        String sql = "SELECT * FROM categorias WHERE id_categoria = ? AND ativo = true";
        Categoria c = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Categoria();
                c.setIdCategoria(rs.getLong("id_categoria"));
                c.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public boolean atualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nome = ? WHERE id_categoria = ? AND ativo = true";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setLong(2, categoria.getIdCategoria());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * MUDANÇA PARA SOFT DELETE
     * Em vez de apagar o registro, apenas marcamos como inativo.
     */
    public boolean remover(Long id) {
        String sql = "UPDATE categorias SET ativo = false WHERE id_categoria = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lista apenas as categorias ativas para o usuário final
    public List<Categoria> listarTodas() {
        String sql = "SELECT * FROM categorias WHERE ativo = true";
        List<Categoria> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getLong("id_categoria"));
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}