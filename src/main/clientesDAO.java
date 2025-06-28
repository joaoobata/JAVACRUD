package main;

import banco.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clientesDAO {
    private Connection coon;

    public clientesDAO() {
        this.coon = new Conexao().getConexao();
    }

    public void inserir(clientes c) {
        String sql = "INSERT INTO clientes (nome, cpf, email) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            stmt.setString(1, c.GetNome());
            stmt.setString(2, c.GetCpf());
            stmt.setString(3, c.GetEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
        }
    }

    public List<clientes> listarClientes() {
        List<clientes> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, email FROM clientes";
        try {
            PreparedStatement stmt = this.coon.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes c = new clientes();
                c.SetId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar clientes: " + ex.getMessage());
        }
        return lista;
    }
}
