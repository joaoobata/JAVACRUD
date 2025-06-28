package main;

import java.math.BigDecimal;

public class notaitem {
    private int id;
    private int notaId;
    private int produtoId;
    private int quantidade;
    private BigDecimal valorUnitario;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getNotaId() {
        return notaId;
    }
    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public int getProdutoId() {
        return produtoId;
    }
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
