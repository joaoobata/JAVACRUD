package main;

import banco.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class categoriaDAO {
    private Conexao conexao;
    private Connection coon;

    public categoriaDAO() {
        this.conexao = new Conexao();
        this.coon = this.conexao.getConexao();
    }

    public void inserir(categoria categoria) {
        String sql = "INSERT INTO categorias (nome, descricao) VALUES (?, ?);";
        
        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir categoria: " + ex.getMessage());
        }
    }

    public List<categoria> listarCategorias() {
        List<categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categoria cat = new categoria();
                cat.SetId(rs.getInt("id"));
                cat.SetNome(rs.getString("nome"));
                cat.SetDescricao(rs.getString("descricao"));
                lista.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar categorias: " + ex.getMessage());
        }
        return lista;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";

        try {
            PreparedStatement stmt = coon.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir categoria: " + ex.getMessage());
        }
    }

    public void editar(categoria categoria) {
        String sql = "UPDATE categorias SET nome = ?, descricao = ? WHERE id = ?";

        try {
            PreparedStatement stmt = coon.prepareStatement(sql);
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getId()); // Corrigido
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar categoria: " + ex.getMessage());
        }
    }
}
