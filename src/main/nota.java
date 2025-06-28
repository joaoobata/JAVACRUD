package main;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class nota {
    private int id;
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private char tipo;             // 'E' ou 'S'
    private Integer clienteId;
    private Integer fornecedorId;
    private List<notaitem> itens;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public char getTipo() {
        return tipo;
    }
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFornecedorId() {
        return fornecedorId;
    }
    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public List<notaitem> getItens() {
        return itens;
    }
    public void setItens(List<notaitem> itens) {
        this.itens = itens;
    }
}
