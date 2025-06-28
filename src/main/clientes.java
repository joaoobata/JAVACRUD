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
public class clientes {

    private int id;
    private String nome;
    private String cpf;
    private String email;

    public int getId() {
        return id;
    }

    public String GetNome() {
        return nome;
    }

    public String GetCpf() {
        return cpf;
    }

    public String GetEmail() {
        return email;
    }

    public void SetId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return GetNome();
    }
}
