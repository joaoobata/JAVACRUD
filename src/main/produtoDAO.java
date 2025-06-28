package main;

import banco.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * DAO responsável pelas operações CRUD na tabela produtos.
 */
public class produtoDAO {
    private Conexao conexao;
    private Connection coon;

    public produtoDAO() {
        this.conexao = new Conexao();
        this.coon = this.conexao.getConexao();
    }

    // INSERIR PRODUTO
    public void inserir(produtos produtos) {
        String sql = "INSERT INTO produtos (nome, preco, categoria_id, cod_barras) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = this.coon.prepareStatement(sql)) {
            stmt.setString(1, produtos.GetNome());
            stmt.setBigDecimal(2, new BigDecimal(Float.toString(produtos.GetPreco())));
            stmt.setInt(3, produtos.GetCategoria());
            stmt.setString(4, produtos.GetCod_Barras());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }

    // EXCLUIR PRODUTO POR ID
    public void excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        try (PreparedStatement stmt = this.coon.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir produto: " + ex.getMessage());
        }
    }

    // EDITAR PRODUTO
    public void editar(produtos produtos) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, categoria_id = ?, cod_barras = ? WHERE id = ?";

        try (PreparedStatement stmt = this.coon.prepareStatement(sql)) {
            stmt.setString(1, produtos.GetNome());
            stmt.setFloat(2, produtos.GetPreco());
            stmt.setInt(3, produtos.GetCategoria());
            stmt.setString(4, produtos.GetCod_Barras());
            stmt.setInt(5, produtos.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    // BUSCAR POR CÓDIGO DE BARRAS
    public produtos buscarPorCodigo(String codBarras) {
        String sql = "SELECT id, nome, preco, categoria_id, cod_barras FROM produtos WHERE cod_barras = ?";

        try (PreparedStatement stmt = coon.prepareStatement(sql)) {
            stmt.setString(1, codBarras);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produtos p = new produtos();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getFloat("preco"));
                    p.setCategoria(rs.getInt("categoria_id"));
                    p.setCod_Barras(rs.getString("cod_barras"));
                    return p;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto por código de barras: " + ex.getMessage());
        }
        return null;
    }

    // BUSCAR POR NOME
    public produtos buscarPorNome(String nome) {
        String sql = "SELECT id, nome, preco, categoria_id, cod_barras FROM produtos WHERE nome = ?";

        try (PreparedStatement stmt = coon.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produtos p = new produtos();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setPreco(rs.getFloat("preco"));
                    p.setCategoria(rs.getInt("categoria_id"));
                    p.setCod_Barras(rs.getString("cod_barras"));
                    return p;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto por nome: " + ex.getMessage());
        }
        return null;
    }

    // LISTAR TODOS OS PRODUTOS
    public List<produtos> listarProdutos() {
        List<produtos> lista = new ArrayList<>();
        String sql = "SELECT id, nome, preco, categoria_id, cod_barras FROM produtos";

        try (PreparedStatement stmt = coon.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                produtos p = new produtos();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setCategoria(rs.getInt("categoria_id"));
                p.setCod_Barras(rs.getString("cod_barras"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar produtos: " + ex.getMessage());
        }
        return lista;
    }
}
