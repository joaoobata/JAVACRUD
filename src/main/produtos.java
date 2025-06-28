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
public class produtos {
    private int id;
    private String nome;
    private float preco;
    private int categoria_id;
    private String cod_barras;
    
    public int getId() {
        return id;
    }
    
    public String GetNome() {
       return nome;
    }
    
    public float GetPreco() {
        return preco;
    }
    
    public int GetCategoria(){
        return categoria_id;
    }
    
    public String GetCod_Barras() {
        return cod_barras;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setPreco(float preco) {
        this.preco = preco;
    }
    
    public void setCategoria(int categoria) {
        this.categoria_id = categoria;
    }
    
    public void setCod_Barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }
}
