package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2830482311004
 */
public class fornecedores {

    private int id;
    private String nome;
    private String nome_fantasia;
    private String cnpj;

    public int GetId() {
        return id;
    }

    public String GetNome() {
        return nome;
    }

    public String GetNomeFantasia() {
        return nome_fantasia;
    }

    public String GetCnpj() {
        return cnpj;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeFantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public void SetCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // em main/fornecedores.java
    @Override
    public String toString() {
        // Ou GetNomeFantasia(), se preferir mostrar o nome fantasia
        return GetNomeFantasia();
    }

}
