package main;

import banco.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fornecedoresDAO {
    private Connection coon;

    public fornecedoresDAO() {
        this.coon = new Conexao().getConexao();
    }

    public void inserir(fornecedores f) {
        String sql = "INSERT INTO fornecedores (nome, nome_fantasia, cnpj) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            stmt.setString(1, f.GetNome());
            stmt.setString(2, f.GetNomeFantasia());
            stmt.setString(3, f.GetCnpj());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir fornecedor: " + ex.getMessage());
        }
    }

    public List<fornecedores> listarFornecedores() {
        List<fornecedores> lista = new ArrayList<>();
        String sql = "SELECT id, nome, nome_fantasia, cnpj FROM fornecedores";
        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fornecedores f = new fornecedores();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setNomeFantasia(rs.getString("nome_fantasia"));
                f.SetCnpj(rs.getString("cnpj"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar fornecedores: " + ex.getMessage());
        }
        return lista;
    }
}
