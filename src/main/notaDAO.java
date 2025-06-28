package main;

import banco.Conexao;
import java.sql.*;
import java.util.List;

public class notaDAO {
    private Connection conn;

    public notaDAO() {
        this.conn = new Conexao().getConexao();
    }

    public int inserirNota(nota n) {
        int notaId = 0;
        String sql = "INSERT INTO nota (data, valor_total, tipo, cliente_id, fornecedor_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setTimestamp(1, Timestamp.valueOf(n.getData()));
            stmt.setBigDecimal(2, n.getValorTotal());
            stmt.setString(3, String.valueOf(n.getTipo()));
            if (n.getClienteId() != null) {
                stmt.setInt(4, n.getClienteId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            if (n.getFornecedorId() != null) {
                stmt.setInt(5, n.getFornecedorId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                notaId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir nota: " + ex.getMessage());
        }
        return notaId;
    }

    public void inserirItens(int notaId, List<notaitem> itens) {
        String sql = "INSERT INTO nota_itens (nota_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (notaitem it : itens) {
                stmt.setInt(1, notaId);
                stmt.setInt(2, it.getProdutoId());
                stmt.setInt(3, it.getQuantidade());
                stmt.setBigDecimal(4, it.getValorUnitario());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir itens da nota: " + ex.getMessage());
        }
    }
}
